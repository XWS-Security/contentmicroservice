package org.nistagram.contentmicroservice.data.repository;

import org.neo4j.springframework.data.repository.query.Query;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("MATCH (p:Post) where p.id = $0 return p")
    Post findByIdentifier(long id);

    @Query("MATCH (u:NistagramUser)-[:LIKES]->(p:Post) WHERE p.id = $0 RETURN u")
    List<NistagramUser> findLikes(long id);

    @Query("MATCH (u:NistagramUser)-[:DISLIKES]->(p:Post) WHERE p.id = $0 RETURN u")
    List<NistagramUser> findDisLikes(long id);

    @Query("MATCH (u:NistagramUser), (p:Post) WHERE u.username = $0 AND p.id = $1 CREATE (u)-[:LIKES]->(p)")
    void addLike(String username, long postId);

    @Query("MATCH (u:NistagramUser), (p:Post) WHERE u.username = $0 AND p.id = $1 CREATE (u)-[:DISLIKES]->(p)")
    void addDislike(String username, long postId);

    @Query("MATCH (u:NistagramUser)-[:LIKES]->(p:Post) WHERE u.username = $0 AND p.id = $1 RETURN p")
    Post findLike(String username, long postId);

    @Query("MATCH (u:NistagramUser)-[:DISLIKES]->(p:Post) WHERE u.username = $0 AND p.id = $1 RETURN p")
    Post findDislike(String username, long postId);

    @Query("MATCH (u:NistagramUser)-[l:LIKES]->(p:Post) WHERE u.username = $0 AND p.id = $1 DELETE l")
    void deleteLike(String username, long postId);

    @Query("MATCH (u:NistagramUser)-[d:DISLIKES]->(p:Post) WHERE u.username = $0 AND p.id = $1 DELETE d")
    void deleteDislike(String username, long postId);
}
