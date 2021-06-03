package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.model.Comment;
import org.nistagram.contentmicroservice.data.model.Location;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.model.content.Story;
import org.nistagram.contentmicroservice.data.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/develop", produces = MediaType.APPLICATION_JSON_VALUE)
public class DevelopController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LocationRepository locationRepository;

    @Value("${PROJECT_PATH}")
    private String project_path;

    public DevelopController(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/")
    public void putData() {
        NistagramUser user1 = new NistagramUser();
        NistagramUser user2 = new NistagramUser();
        NistagramUser user3 = new NistagramUser();
        var contentList = new ArrayList<Content>();
        var post1 = new Post();
        var post2 = new Post();
        var post3 = new Post();
        var post4 = new Post();
        var post5 = new Post();
        var story1 = new Story();
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

        var paths5 = new ArrayList<String>();
        paths5.add("image9.jpg");

        var paths6 = new ArrayList<String>();
        paths6.add("video1.mp4");
        paths6.add("video2.mp4");
        paths6.add("image10.jpg");

        Location location1 = new Location(1L, "Splavovi Beograd");

        post1.setPaths(paths1);
        post2.setPaths(paths2);
        post3.setPaths(paths3);
        post4.setPaths(paths4);
        post5.setPaths(paths6);
        story1.setPaths(paths5);
        Date date = new Date();
        post1.setDate(date);
        post2.setDate(date);
        post3.setDate(date);
        post4.setDate(date);
        post5.setDate(date);
        story1.setDate(date);

        contentList.add(post1);
        contentList.add(post2);
        contentList.add(post3);
        contentList.add(post4);
        contentList.add(post5);
        contentList.add(story1);

        user1.setContent(contentList);
        user1.setUsername("_cvjeticaninLazo98");
        user1.setAbout("Zivot me nije mazio. Programer.");
        user1.setProfilePictureName("Profile1.jpg");

        user2.setUsername("_cze_tka1389");
        user2.setAbout("VOLEM SANGARIJEPE ŽDRIT!!!!");
        user2.setProfilePictureName("Profile2.jpg");

        user3.setUsername("ZDREBCEBA_KRF_03");
        user3.setAbout("Mozes da me povredis, ale ne i da me povredis!!!!");
        user3.setProfilePictureName("Profile3.jpg");

        user2.setCloseFriends(new ArrayList<>());
        user2.setReportedComments(new HashMap<>());
        user2.setSavedContent(new ArrayList<>());
        user2.setSubscribedUsers(new ArrayList<>());

        user3.setCloseFriends(new ArrayList<>());
        user3.setReportedComments(new HashMap<>());
        user3.setSavedContent(new ArrayList<>());
        user3.setSubscribedUsers(new ArrayList<>());

        userRepository.save(user2);
        userRepository.save(user3);

        Comment comment1 = new Comment(1L, "Uffff preslatkasi ;)", date, user2);
        Comment comment2 = new Comment(2L, "Aaaa nisaaaaam <333", date, user3);
        Comment comment3 = new Comment(3L, "Ja sam zbognje naviko na tugu. :(", date, user3);

        commentRepository.save(comment1);
        //commentRepository.save(comment2);
        commentRepository.save(comment3);
        locationRepository.save(location1);
        var tags = new ArrayList<String>();
        tags.add("#VSCO");
        tags.add("#VSCO_BALCAN");

        var comments1 = new ArrayList<Comment>();
        comments1.add(comment1);
        //comments1.add(comment2);
        comments1.add(comment3);

        var likes1 = new ArrayList<NistagramUser>();
        likes1.add(user2);
        post1.setComments(comments1);
        post1.setLocation(location1);
        post1.setTags(tags);
        post1.setLikes(likes1);
        post1.setAbout("Last night a Dj saved my life <3 !!!");

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        user1.setCloseFriends(new ArrayList<>());
        user1.setReportedComments(new HashMap<>());
        user1.setSavedContent(new ArrayList<>());
        user1.setSubscribedUsers(new ArrayList<>());

        userRepository.save(user1);
    }
}
