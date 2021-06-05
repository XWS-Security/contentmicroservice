package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.CommentDto;
import org.nistagram.contentmicroservice.data.dto.CreatePostDto;
import org.nistagram.contentmicroservice.data.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;
import org.nistagram.contentmicroservice.data.dto.PostsUserDto;

import javax.net.ssl.SSLException;
import java.util.List;

public interface IPostService {
    List<String> getImageNames(Long id);

    PostDto getPostInfo(Long id);

    CommentDto getComment(Long id);

    PostsUserDto getPostsUser(Long id);

    void createPost(CreatePostDto postDto, List<MultipartFile> files) throws SSLException;
}
