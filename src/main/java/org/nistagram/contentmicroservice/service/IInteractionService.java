package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.enums.CloseFriends;

public interface IInteractionService {

    CloseFriends getCloseFriendStatus(String id);

    void removeCloseFriend(String username);

    void addCloseFriend(String username);
}
