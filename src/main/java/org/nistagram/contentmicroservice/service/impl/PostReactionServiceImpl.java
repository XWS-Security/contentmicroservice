package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.UploadCommentDto;
import org.nistagram.contentmicroservice.data.model.Comment;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.repository.PostRepository;
import org.nistagram.contentmicroservice.exceptions.PostDoesNotExistException;
import org.nistagram.contentmicroservice.service.PostReactionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PostReactionServiceImpl implements PostReactionService {
    private final PostRepository postRepository;

    public PostReactionServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void comment(UploadCommentDto dto) {
        Post post = getPost(dto.getPostId());

        // TODO: get post owner and validate request (follow microservice)

        Comment comment = new Comment(dto.getText(), new Date(), getCurrentlyLoggedUser());
        post.getComments().add(comment);
        postRepository.save(post);
    }

    @Override
    public void like(long postId) {
        Post post = getPost(postId);
        NistagramUser user = getCurrentlyLoggedUser();
        // TODO: get post owner and validate request (follow microservice)

        post.getDislikes().remove(user);
        if (!post.getLikes().contains(user)) {
            post.getLikes().add(user);
            postRepository.save(post);
        }
    }

    @Override
    public void dislike(long postId) {
        Post post = getPost(postId);
        NistagramUser user = getCurrentlyLoggedUser();
        // TODO: get post owner and validate request (follow microservice)

        post.getLikes().remove(user);
        if (!post.getDislikes().contains(user)) {
            post.getDislikes().add(user);
            postRepository.save(post);
        }
    }

    Post getPost(long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new PostDoesNotExistException(postId);
        }
        return post.get();
    }

    void validateUser(NistagramUser owner) {
        // TODO: check if user has access to poster's content
    }

    private NistagramUser getCurrentlyLoggedUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            return null;
        } else {
            return (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }
}
