package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.CommentDto;
import org.nistagram.contentmicroservice.data.dto.LocationDto;
import org.nistagram.contentmicroservice.data.dto.PostDto;
import org.nistagram.contentmicroservice.data.dto.PostsUserDto;
import org.nistagram.contentmicroservice.data.repository.CommentRepository;
import org.nistagram.contentmicroservice.data.repository.PostRepository;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.IPostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<String> getImageNames(Long id) {
        var optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new NotFoundException(id);
        } else {
            var post = optionalPost.get();
            return post.getPaths();
        }
    }

    @Override
    public PostDto getPostInfo(Long id) {
        var optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new NotFoundException(id);
        } else {
            var post = optionalPost.get();
            var location = new LocationDto(post.getLocation());
            var tags = post.getTags();
            var date = post.getDate();
            var likes = post.getLikes().size();
            var dislikes = post.getDislikes().size();
            var comments = post.getComments();
            var about = post.getAbout();
            List<Long> commentIds = new ArrayList<>();
            comments.forEach(comment -> commentIds.add(comment.getId()));
            return new PostDto(location, tags, date, likes, dislikes, commentIds, about);
        }
    }


    @Override
    public CommentDto getComment(Long id) {
        var optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException(id);
        }
        var comment = optional.get();
        var text = comment.getText();
        var date = comment.getDate();
        var user = comment.getUser();
        var imageName = user.getProfilePictureName();
        var username = user.getUsername();

        return new CommentDto(username, date, imageName, text, id);
    }

    @Override
    public PostsUserDto getPostsUser(Long id) {
        var user = userRepository.findByContentContaining(id);
        return new PostsUserDto(user.getUsername(), user.getProfilePictureName());
    }
}
