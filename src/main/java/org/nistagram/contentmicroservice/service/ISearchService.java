package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.ProfileImageDto;

import java.util.Set;

public interface ISearchService {
    Set<ProfileImageDto> searchByLocation(String locationName);

    Set<ProfileImageDto> searchByTag(String tag);

    Set<ProfileImageDto> searchByTagAndLocation(String name);
}
