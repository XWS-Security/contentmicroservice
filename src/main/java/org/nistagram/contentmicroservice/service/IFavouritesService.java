package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.PostImageLinkDto;

import java.util.List;

public interface IFavouritesService {
    List<PostImageLinkDto> getFavourites();

    void saveOrRemoveFavourite(Long postId);

    boolean inFavourites(Long postId);
}
