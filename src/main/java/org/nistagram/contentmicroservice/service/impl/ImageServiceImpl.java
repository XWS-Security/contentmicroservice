package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.service.IImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ImageServiceImpl implements IImageService {

    @Value("${PROJECT_PATH}")
    private String project_path;

    @Override
    public File getImage(String name) {
        File img = new File(project_path + name);
        return img;
    }
}
