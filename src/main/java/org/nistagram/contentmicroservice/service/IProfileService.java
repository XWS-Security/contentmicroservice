package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.ProfileInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProfileService {
    ProfileInfoDto getUserInfo(String id);

    void setProfilePicture(List<MultipartFile> files) throws IOException;
}
