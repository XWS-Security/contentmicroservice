package org.nistagram.contentmicroservice.service.impl;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.nistagram.contentmicroservice.data.dto.*;
import org.nistagram.contentmicroservice.data.model.Comment;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.repository.CommentRepository;
import org.nistagram.contentmicroservice.data.repository.PostRepository;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.AccessToUserProfileDeniedException;
import org.nistagram.contentmicroservice.exceptions.PostDoesNotExistException;
import org.nistagram.contentmicroservice.service.PostReactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostReactionServiceImpl implements PostReactionService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Value("${FOLLOWER}")
    private String followerMicroserviceURI;

    public PostReactionServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void comment(UploadCommentDto dto, String token) throws AccessToUserProfileDeniedException, SSLException {
        Post post = getPost(dto.getPostId());
        NistagramUser owner = userRepository.findByContentContaining(dto.getPostId());
        validateUser(owner, token);

        Comment comment = new Comment(dto.getText(), new Date(), getCurrentlyLoggedUser());
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        comments.add(comment);
        post.setComments(comments);
        commentRepository.save(comment);
        postRepository.save(post);
    }

    @Override
    public void like(long postId, String token) throws AccessToUserProfileDeniedException, SSLException {
        Post post = getPost(postId);
        NistagramUser user = getCurrentlyLoggedUser();
        NistagramUser owner = userRepository.findByContentContaining(postId);
        validateUser(owner, token);

        var dislikes = userRepository.findDislikesForPost(postId);
        dislikes.remove(user);
        post.setDislikes(dislikes);

        var likes = userRepository.findLikesForPost(postId);
        if (!likes.contains(user)) {
            likes.add(user);
            post.setLikes(likes);
        }

        postRepository.save(post);
    }

    @Override
    public void dislike(long postId, String token) throws AccessToUserProfileDeniedException, SSLException {
        Post post = getPost(postId);
        NistagramUser user = getCurrentlyLoggedUser();
        NistagramUser owner = userRepository.findByContentContaining(postId);
        validateUser(owner, token);

        var likes = userRepository.findLikesForPost(postId);
        likes.remove(user);
        post.setLikes(likes);

        var dislikes = userRepository.findDislikesForPost(postId);
        if (!dislikes.contains(user)) {
            dislikes.add(user);
            post.setDislikes(dislikes);
        }

        postRepository.save(post);
    }

    @Override
    public List<PostImageLinkDto> getLikedPosts() {
        ArrayList<Post> allPosts = (ArrayList<Post>) postRepository.findAll();
        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<PostImageLinkDto> likedPosts = new ArrayList<>();

        allPosts.forEach(post -> {
            if (post.getLikes().contains(user)) {
                var postOwner = userRepository.findByContentContaining(post.getId());
                likedPosts.add(new PostImageLinkDto(post.getPathsList().get(0), post.getId(), postOwner.getUsername(), postOwner.getProfilePictureName()));
            }
        });

        return likedPosts;
    }

    @Override
    public List<PostImageLinkDto> getDislikedPosts() {
        ArrayList<Post> allPosts = (ArrayList<Post>) postRepository.findAll();
        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<PostImageLinkDto> dislikedPosts = new ArrayList<>();

        allPosts.forEach(post -> {
            if (post.getDislikes().contains(user)) {
                var postOwner = userRepository.findByContentContaining(post.getId());
                dislikedPosts.add(new PostImageLinkDto(post.getPathsList().get(0), post.getId(), postOwner.getUsername(), postOwner.getProfilePictureName()));
            }
        });

        return dislikedPosts;
    }

    Post getPost(long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new PostDoesNotExistException(postId);
        }
        return post.get();
    }

    private void validateUser(NistagramUser owner, String token) throws AccessToUserProfileDeniedException, SSLException {
        NistagramUser user = getCurrentlyLoggedUser();

        if (!owner.getProfilePrivate()) {
            return;
        }
        if (owner.getUsername().equals(user.getUsername())) {
            return;
        }

        String followee = owner.getUsername();

        // SSL
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        // Creating web client.
        WebClient client = WebClient.builder()
                .baseUrl(followerMicroserviceURI)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        // Define a method.
        var result = client.get()
                .uri("/users/hasAccess/" + followee)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(FollowRequestAccessResponseDto.class)
                .block();

        if (result == null) {
            throw new AccessToUserProfileDeniedException("No response");
        }
        if (!result.isAccessAllowed()) {
            throw new AccessToUserProfileDeniedException(result.getMessage());
        }
    }

    private NistagramUser getCurrentlyLoggedUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            return null;
        } else {
            return (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }
}
