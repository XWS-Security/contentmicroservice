package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.MediaFileDto;

import java.io.IOException;

public interface IImageService {
    MediaFileDto getImage(String name) throws IOException;
}
