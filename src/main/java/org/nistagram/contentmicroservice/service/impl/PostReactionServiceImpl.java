package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.UploadCommentDto;
import org.nistagram.contentmicroservice.data.model.Comment;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.repository.CommentRepository;
import org.nistagram.contentmicroservice.data.repository.PostRepository;
import org.nistagram.contentmicroservice.exceptions.PostDoesNotExistException;
import org.nistagram.contentmicroservice.service.PostReactionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PostReactionServiceImpl implements PostReactionService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostReactionServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void comment(UploadCommentDto dto) {
        Post post = postRepository.findByIdentifier(dto.getPostId());
        validatePost(post, dto.getPostId());

        // TODO: get post owner and validate request (follow microservice)

        long id = ThreadLocalRandom.current().nextLong(100000);
        Comment comment = new Comment(id, dto.getText(), new Date(), getCurrentlyLoggedUser());
        commentRepository.saveWithPost(post.getId(), comment.getText(), comment.getDate(), comment.getId(), comment.getUser().getUsername());
    }

    @Override
    public void like(long postId) {
        Post post = postRepository.findByIdentifier(postId);
        validatePost(post, postId);

        // TODO: get post owner and validate request (follow microservice)

        String username = getCurrentlyLoggedUser().getUsername();
        Post dislike = postRepository.findDislike(username, postId);
        if (dislike != null) {
            postRepository.deleteDislike(username, postId);
        }

        postRepository.addLike(username, postId);
    }

    @Override
    public void dislike(long postId) {
        Post post = postRepository.findByIdentifier(postId);
        validatePost(post, postId);

        // TODO: get post owner and validate request (follow microservice)

        String username = getCurrentlyLoggedUser().getUsername();
        Post like = postRepository.findLike(username, postId);
        if (like != null) {
            postRepository.deleteLike(username, postId);
        }

        postRepository.addDislike(username, postId);
    }

    void validatePost(Post post, long postId) {
        if (post == null) {
            throw new PostDoesNotExistException(postId);
        }
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
