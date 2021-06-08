package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.model.*;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.model.content.Story;
import org.nistagram.contentmicroservice.data.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/develop", produces = MediaType.APPLICATION_JSON_VALUE)
public class DevelopController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LocationRepository locationRepository;
    private final StoryRepository storyRepository;
    private final RoleRepository roleRepository;

    @Value("${PROJECT_PATH}")
    private String project_path;

    public DevelopController(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LocationRepository locationRepository, StoryRepository storyRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.locationRepository = locationRepository;
        this.storyRepository = storyRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public void putData() {
        Role role = new Role("NISTAGRAM_USER_ROLE");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roleRepository.save(role);

        NistagramUser luka = new NistagramUser();
        NistagramUser vlado = new NistagramUser();
        NistagramUser vidoje = new NistagramUser();
        NistagramUser milica = new NistagramUser();
        NistagramUser duja = new NistagramUser();
        NistagramUser kobra = new NistagramUser();

        luka.setRoles(roles);
        vlado.setRoles(roles);
        vidoje.setRoles(roles);
        milica.setRoles(roles);
        duja.setRoles(roles);
        kobra.setRoles(roles);

        luka.setUsername("luka");
        luka.setAbout("Zivot me nije mazio. Programer.");
        luka.setProfilePictureName("Profile1.jpg");

        vlado.setUsername("vlado");
        vlado.setAbout("VOLEM SANGARIJEPE ŽDRIT!!!!");
        vlado.setProfilePictureName("Profile2.jpg");

        kobra.setUsername("kobra");
        kobra.setAbout("Mozes da me povredis, ale ne i da me povredis!!!!");
        kobra.setProfilePictureName("Profile3.jpg");

        vidoje.setUsername("vidoje");
        vidoje.setAbout("Ja msm da ovaj cak i ima.");
        vidoje.setProfilePictureName("profile4.jpg");

        milica.setUsername("milica");
        milica.setAbout("Roli <3");
        milica.setProfilePictureName("profile5.jpg");

        duja.setUsername("duja");
        duja.setAbout("Kyrie <3 Nba <3");
        duja.setProfilePictureName("profile6.jpg");

//        vlado.setCloseFriends(new ArrayList<>());
//        vlado.setReportedComments(new HashMap<>());
//        vlado.setSavedContent(new ArrayList<>());
//        vlado.setSubscribedUsers(new ArrayList<>());

//        kobra.setCloseFriends(new ArrayList<>());
//        kobra.setReportedComments(new HashMap<>());
//        kobra.setSavedContent(new ArrayList<>());
//        kobra.setSubscribedUsers(new ArrayList<>());

        userRepository.save(vlado);
        userRepository.save(kobra);

//        luka.setCloseFriends(new ArrayList<>());
//        luka.setReportedComments(new HashMap<>());
//        luka.setSavedContent(new ArrayList<>());
//        luka.setSubscribedUsers(new ArrayList<>());

        userRepository.save(luka);
        userRepository.save(duja);
        userRepository.save(milica);
        userRepository.save(vidoje);

        var contentList = new ArrayList<Content>();
        var posts = new ArrayList<Post>();
        var stories = new ArrayList<Story>();
        var post1 = new Post();
        var post2 = new Post();
        var post3 = new Post();
        var post4 = new Post();
        var post5 = new Post();

        var story1 = new Story();
        var story2 = new Story();
        var story3 = new Story();
        var story4 = new Story();

        story1.setId(6L);
        story2.setId(7L);
        story3.setId(8L);
        story4.setId(9L);

        var paths1 = new ArrayList<String>();
        paths1.add("image1.jpg");
        paths1.add("image2.jpg");

        var paths2 = new ArrayList<String>();
        paths2.add("image3.jpg");
        paths2.add("image4.jpg");

        var paths3 = new ArrayList<String>();
        paths3.add("image5.jpg");
        paths3.add("image6.jpg");

        var paths4 = new ArrayList<String>();
        paths4.add("image7.jpg");
        paths4.add("image8.jpg");

        var paths6 = new ArrayList<String>();
        paths6.add("video1.mp4");
        paths6.add("video2.mp4");
        paths6.add("image10.jpg");

        Location location1 = new Location();
        location1.setName("Novi Sad");

        var tags = new ArrayList<String>();
        tags.add("#VSCO");
        tags.add("#VSCO_BALCAN");

        post1.setId(1L);
        post2.setId(2L);
        post3.setId(3L);
        post4.setId(4L);
        post5.setId(5L);

        post1.setPathsList(paths1);
        post2.setPathsList(paths2);
        post3.setPathsList(paths3);
        post4.setPathsList(paths4);
        post5.setPathsList(paths6);

        story1.setPath("image9.jpg");
        story1.setHighlights(true);
        story1.setTagsList(tags);
        story1.setLocation(location1);
        story2.setPath("image1.jpg");
        story2.setHighlights(true);
        story3.setPath("image2.jpg");
        story4.setPath("image3.jpg");

        Date date = new Date();
        Date date1 = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DAY_OF_MONTH, 3);

        date1.setTime(calendar.getTimeInMillis());
        post1.setDate(date);
        post2.setDate(date);
        post3.setDate(date);
        post4.setDate(date);
        post5.setDate(date);

        story1.setDate(date);
        story2.setDate(date);
        story3.setDate(date);
        story4.setDate(date1);

        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);

        stories.add(story1);
        stories.add(story2);
        stories.add(story3);
        stories.add(story4);

        contentList.addAll(posts);
        contentList.addAll(stories);

        locationRepository.save(location1);
        postRepository.saveAll(posts);
        storyRepository.saveAll(stories);
        luka.setContent(contentList);
        userRepository.save(luka);

        Comment comment1 = new Comment(111L, "Uffff preslatkasi ;)", date, vlado);
        Comment comment2 = new Comment(222L, "Aaaa nisaaaaam <333", date, luka);
        Comment comment3 = new Comment(333L, "Ja sam zbognje naviko na tugu. :(", date, kobra);

        var comments1 = new ArrayList<Comment>();
        comments1.add(comment1);
        comments1.add(comment2);
        comments1.add(comment3);

        var likes1 = new ArrayList<NistagramUser>();
        likes1.add(vlado);
        post1.setComments(comments1);
        post1.setLocation(location1);
        post1.setTagsList(tags);
        post1.setLikes(likes1);
        post1.setAbout("Last night a Dj saved my life <3 !!!");

        commentRepository.saveAll(comments1);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

        storyRepository.save(story1);
        storyRepository.save(story2);
        storyRepository.save(story3);
        storyRepository.save(story4);
    }

    @GetMapping("/auth")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    public ResponseEntity<String> getLoggedInUser() {
        try {
            User user = getCurrentlyLoggedUser();
            return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private User getCurrentlyLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
