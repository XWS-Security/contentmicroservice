package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.model.content.Story;
import org.nistagram.contentmicroservice.data.repository.ContentRepository;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/develop", produces = MediaType.APPLICATION_JSON_VALUE)
public class DevelopController {

    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    @Value("${PROJECT_PATH}")
    private String project_path;

    public DevelopController(UserRepository userRepository, ContentRepository contentRepository) {
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    @GetMapping("/")
    public void putData() {
        NistagramUser user1 = new NistagramUser();
        var contentList = new ArrayList<Content>();
        var post1 = new Post();
        var post2 = new Post();
        var post3 = new Post();
        var post4 = new Post();
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
        paths5.add("image10.jpg");

        post1.setPaths(paths1);
        post2.setPaths(paths2);
        post3.setPaths(paths3);
        post4.setPaths(paths4);
        story1.setPaths(paths5);
        Date date = new Date();
        post1.setDate(date);
        post2.setDate(date);
        post3.setDate(date);
        post4.setDate(date);
        story1.setDate(date);

        contentList.add(post1);
        contentList.add(post2);
        contentList.add(post3);
        contentList.add(post4);
        contentList.add(story1);

        user1.setContent(contentList);
        user1.setUsername("_cvjeticaninLazo98");
        user1.setAbout("Zivot me nije mazio. Programer.");
        user1.setProfilePictureName("Profile1Name.jpg");
        contentRepository.save(post1);
        contentRepository.save(post2);
        contentRepository.save(post3);
        contentRepository.save(post4);

        contentRepository.save(story1);
        user1.setCloseFriends(new ArrayList<>());
        user1.setReportedComments(new HashMap<>());
        user1.setSavedContent(new ArrayList<>());
        user1.setSubscribedUsers(new ArrayList<>());
        userRepository.save(user1);
    }
}
