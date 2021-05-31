package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.ProfileInfoDto;

public interface IProfileService {
    ProfileInfoDto getUserInfo(String id);
}
