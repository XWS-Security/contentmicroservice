package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLException;
import java.util.List;

public interface IPostService {
    List<String> getImageNames(Long id);

    PostDto getPostInfo(Long id);

    CommentDto getComment(Long id);

    PostsUserDto getPostsUser(Long id);

    void createPost(CreatePostDto postDto, List<MultipartFile> files) throws SSLException;

    List<LocationDto> getAllLocations();

    void removePost(Long postId);
}
