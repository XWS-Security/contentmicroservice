package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.*;
import org.nistagram.contentmicroservice.data.model.Location;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.Report;
import org.nistagram.contentmicroservice.data.model.User;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.repository.*;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.exceptions.PostDoesNotExistException;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
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
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final NistagramUserRepository nistagramUserRepository;
    private final LocationRepository locationRepository;
    private final ContentRepository contentRepository;
    private final ReportRepository reportRepository;

    @Value("${PROJECT_PATH}")
    private String project_path;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, LocationRepository locationRepository, NistagramUserRepository nistagramUserRepository, ContentRepository contentRepository, ReportRepository reportRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.locationRepository = locationRepository;
        this.nistagramUserRepository = nistagramUserRepository;
        this.contentRepository = contentRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public List<String> getImageNames(Long id) {
        var post = getPost(id);
        return post.getPathsList();
    }

    @Override
    public PostDto getPostInfo(Long id) {
        var post = getPost(id);
        var l = post.getLocation();
        LocationDto location;
        if (l != null) {
            location = new LocationDto(l);
        } else location = null;
        var tags = post.getTagsList();
        var date = post.getDate();
        var likes = post.getLikes().size();
        var dislikes = post.getDislikes().size();
        var comments = post.getComments();
        var about = post.getAbout();
        List<Long> commentIds = new ArrayList<>();
        comments.forEach(comment -> commentIds.add(comment.getId()));
        var taggedUsers = post.getTaggedUsers().stream().map(NistagramUser::getUsername).collect(Collectors.toList());
        return new PostDto(location, tags, date, likes, dislikes, commentIds, about, taggedUsers);

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
        var user = nistagramUserRepository.findByContentContaining(id);
        return new PostsUserDto(user.getUsername(), user.getProfilePictureName());
    }

    @Override
    public void createPost(CreatePostDto postDto, List<MultipartFile> files) {
        Post post = new Post();
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

        post.setPathsList(paths);
        post.setTagsList(postDto.getTags());
        post.setAbout(postDto.getAbout());
        post.setDate(Calendar.getInstance().getTime());
        post.setLocation(locationRepository.findByName(postDto.getLocation()));

        List<NistagramUser> taggedUsers = new ArrayList<>();
        for (String username : postDto.getTaggedUsers()) {
            NistagramUser taggedUser = nistagramUserRepository.findByUsername(username);
            if (taggedUser.isTagsEnabled() && !taggedUsers.contains(taggedUser)) {
                taggedUsers.add(taggedUser);
            }
        }
        post.setTaggedUsers(taggedUsers);

        postRepository.save(post);

        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Content> userContent = contentRepository.findAllByUserId(user.getId());
        userContent.add(post);
        user.setContent(userContent);
        nistagramUserRepository.save(user);
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

    @Override
    public void removePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostImageLinkDto> getUsersPosts() {
        NistagramUser user = getCurrentlyLoggedUser();
        List<Post> posts = postRepository.findAllByUserId(user.getId());
        List<PostImageLinkDto> dtos = new ArrayList<>();
        posts.forEach(post -> {
            dtos.add(new PostImageLinkDto(post.getPaths()[0], post.getId(), user.getUsername(), user.getProfilePictureName()));
        });
        return dtos;
    }

    Post getPost(long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new PostDoesNotExistException(postId);
        }
        return post.get();
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof NistagramUser) {
            return (NistagramUser) object;
        }
        throw new UserNotLogged();
    }
}
