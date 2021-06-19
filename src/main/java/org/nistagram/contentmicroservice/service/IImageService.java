package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.MediaFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    MediaFileDto getImage(String name) throws IOException;
    String saveOne(List<MultipartFile> files) throws IOException;
}
