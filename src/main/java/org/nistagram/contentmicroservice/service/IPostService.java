package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.CommentDto;
import org.nistagram.contentmicroservice.data.dto.PostDto;

import java.util.List;

public interface IPostService {
    List<String> getImageNames(Long id);

    PostDto getPostInfo(Long id);

    CommentDto getComment(Long id);
}
