package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.CommentDto;
import org.nistagram.contentmicroservice.data.dto.CreatePostDto;
import org.nistagram.contentmicroservice.data.dto.LocationDto;
import org.nistagram.contentmicroservice.data.dto.PostDto;
import org.nistagram.contentmicroservice.data.dto.PostsUserDto;
import org.nistagram.contentmicroservice.data.model.Location;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.repository.CommentRepository;
import org.nistagram.contentmicroservice.data.repository.LocationRepository;
import org.nistagram.contentmicroservice.data.repository.PostRepository;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.exceptions.PostDoesNotExistException;
import org.nistagram.contentmicroservice.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Value("${PROJECT_PATH}")
    private String project_path;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, LocationRepository locationRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<String> getImageNames(Long id) {
        var post = getPost(id);
        return post.getPaths();
    }

    @Override
    public PostDto getPostInfo(Long id) {
        var post = getPost(id);
        var l = post.getLocation();
        LocationDto location;
        if (l != null) {
            location = new LocationDto(l);
        } else location = null;
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
    public void createPost(CreatePostDto postDto, List<MultipartFile> files) {
        Post post = new Post();
        long postId = ThreadLocalRandom.current().nextLong(100000);
        List<String> paths = new ArrayList<>();
        Path post_path = Paths.get(project_path);

        files.forEach(file -> {
            try {
                String extension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
                String random = UUID.randomUUID().toString() + "." + extension;
                Files.copy(file.getInputStream(), post_path.resolve(random));
                paths.add(random);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        post.setPaths(paths);
        post.setTags(postDto.getTags());
        post.setAbout(postDto.getAbout());
        post.setDate(Calendar.getInstance().getTime());
        post.setId(postId);
        post.setLocation(locationRepository.findByName(postDto.getLocation()));
        postRepository.save(post);

        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Content> userContent = user.getContent();
        userContent.add(post);
        userRepository.save(user);
    }

    @Override
    public List<LocationDto> getAllLocations() {
        ArrayList<Location> locations = (ArrayList<Location>) locationRepository.findAll();
        ArrayList<LocationDto> locationDtos = new ArrayList<>();

        locations.forEach(location -> {
            locationDtos.add(new LocationDto(location));
        });

        return locationDtos;
    }

    Post getPost(long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new PostDoesNotExistException(postId);
        }
        return post.get();
    }
}
