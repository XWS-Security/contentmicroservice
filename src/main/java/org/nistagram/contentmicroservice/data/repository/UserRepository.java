package org.nistagram.contentmicroservice.data.repository;

import org.neo4j.springframework.data.repository.query.Query;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<NistagramUser, String> {
    NistagramUser findByUsername(String username);

    @Query("MATCH (p:NistagramUser) WHERE p.username = $0 SET p.username = $1, p.private = $2 RETURN p")
    NistagramUser updateProperties(String oldUsername, String username, boolean profilePrivate);

    @Query("MATCH(p:Content)-[c:CONTENTS]->(n:NistagramUser) WHERE p.id=$0 return n")
    NistagramUser findByContentContaining(Long id);

    @Query("MATCH(p:Post)<-[c:LIKES]-(n:NistagramUser) WHERE p.id=$0 return n")
    List<NistagramUser> findAllWhoLikedPost(long id);

    @Query("MATCH(p:Post)<-[c:DISLIKES]-(n:NistagramUser) WHERE p.id=$0 return n")
    List<NistagramUser> findAllWhoDislikedPost(long id);

    @Query("MATCH(u:NistagramUser)<-[c:IS_CLOSE_FRIEND]-(n:NistagramUser) WHERE u.username=$0 return n")
    List<NistagramUser> findAllCloseFriends(String username);

    @Query("MATCH(u:NistagramUser)<-[c:IS_CLOSE_FRIEND]-(cf:NistagramUser) WHERE u.username=$0 AND cf.username=$1 delete c")
    List<NistagramUser> removeCloseFriend(String username,String friendUsername);

    @Query("MATCH(u:NistagramUser), (cf:NistagramUser) WHERE u.username=$0 AND cf.username=$1 CREATE (cf)-[:IS_CLOSE_FRIEND]->(u)")
    List<NistagramUser> addCloseFriend(String username,String friendUsername);
}
