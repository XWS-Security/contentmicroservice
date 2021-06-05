package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.CommentDto;
import org.nistagram.contentmicroservice.data.dto.CreatePostDto;
import org.nistagram.contentmicroservice.data.dto.LocationDto;
import org.nistagram.contentmicroservice.data.dto.PostDto;
import org.nistagram.contentmicroservice.data.dto.PostsUserDto;
import org.nistagram.contentmicroservice.data.repository.CommentRepository;
import org.nistagram.contentmicroservice.data.repository.PostRepository;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.keystore.Keystore;
import org.nistagram.contentmicroservice.service.IPostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Value("${ACCOUNT}")
    private String accountMicroserviceURI;
    private final Keystore keystore = new Keystore();

    @Value("${PROJECT_PATH}")
    private String project_path;

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

    @Override
    public void createPost(CreatePostDto postDto, List<MultipartFile> files) throws SSLException {

        // TODO: Get logged user from token.

        String postUID = UUID.randomUUID().toString();
        File f = new File(project_path + "/username" + "/" + "post" + "-" + postUID);
        f.mkdirs();

        Path postFolder = Paths.get(project_path + "/username" + "/" + "post" + "-" + postUID);

        System.out.println(postDto.getAbout());

        files.forEach(file -> {
            try {
                System.out.println(file.getOriginalFilename());
                Files.copy(file.getInputStream(), postFolder.resolve(UUID.randomUUID().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
