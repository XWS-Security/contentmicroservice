package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<NistagramUser, Long> {
    NistagramUser findByUsername(String username);

    // TODO: save
    @Query("MATCH (p:NistagramUser) WHERE p.username = $0 SET p.username = $1, p.private = $2 RETURN p")
    NistagramUser updateProperties(String oldUsername, String username, boolean profilePrivate);

    // TODO: get content
    @Query("MATCH(p:Content)-[c:CONTENTS]->(n:NistagramUser) WHERE p.id=$0 return n")
    NistagramUser findByContentContaining(Long id);

    // TODO: getLikes
    @Query("MATCH(p:Post)<-[c:LIKES]-(n:NistagramUser) WHERE p.id=$0 return n")
    List<NistagramUser> findAllWhoLikedPost(long id);

    // TODO: getDislikes
    @Query("MATCH(p:Post)<-[c:DISLIKES]-(n:NistagramUser) WHERE p.id=$0 return n")
    List<NistagramUser> findAllWhoDislikedPost(long id);

    // TODO: getCloseFriends
    @Query("MATCH(u:NistagramUser)<-[c:IS_CLOSE_FRIEND]-(n:NistagramUser) WHERE u.username=$0 return n")
    List<NistagramUser> findAllCloseFriends(String username);

    // TODO: getCloseFriends and save
    @Query("MATCH(u:NistagramUser)<-[c:IS_CLOSE_FRIEND]-(cf:NistagramUser) WHERE u.username=$0 AND cf.username=$1 delete c")
    List<NistagramUser> removeCloseFriend(String username, String friendUsername);

    // TODO: getCloseFriends and save
    @Query("MATCH(u:NistagramUser), (cf:NistagramUser) WHERE u.username=$0 AND cf.username=$1 CREATE (cf)-[:IS_CLOSE_FRIEND]->(u)")
    void addCloseFriend(String username, String friendUsername);

    // TODO: getSaved and save
    @Query("MATCH(u:NistagramUser), (p:Post) WHERE u.username=$0 AND p.id=$1 CREATE (p)-[:SAVED]->(u)")
    void savePost(String username, Long postId);

    // TODO: getSaved and save
    @Query("MATCH(u:NistagramUser)<-[s:SAVED]-(p:Post) WHERE u.username=$0 AND p.id=$1 delete s")
    void removeSavedPost(String username, Long postId);
}
