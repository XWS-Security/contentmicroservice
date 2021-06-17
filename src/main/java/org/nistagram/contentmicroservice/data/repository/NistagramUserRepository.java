package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NistagramUserRepository extends CrudRepository<NistagramUser, Long> {
    NistagramUser findByUsername(String username);

    @Query(value = "SELECT DISTINCT u.id, u.user_type, u.username, u.enabled, u.last_password_reset_date, u.profile_picture," +
            " u.about, u.private_profile FROM nistagram_user AS u, content AS c, user_content AS uc" +
            " WHERE u.user_type = 'INSTAGRAM_USER' AND u.id = uc.user_id AND c.id = uc.content_id AND c.id = :id", nativeQuery = true)
    NistagramUser findByContentContaining(Long id);

    @Query(value = "SELECT DISTINCT u.id, u.user_type, u.username, u.enabled, u.last_password_reset_date, u.profile_picture," +
            " u.about, u.private_profile FROM nistagram_user AS u, content AS c, post_likes AS pl" +
            " WHERE u.user_type = 'INSTAGRAM_USER' AND u.id = pl.user_id AND c.id = pl.post_id AND c.id = :id", nativeQuery = true)
    List<NistagramUser> findLikesForPost(long id);

    @Query(value = "SELECT DISTINCT u.id, u.user_type, u.username, u.enabled, u.last_password_reset_date, u.profile_picture," +
            " u.about, u.private_profile FROM nistagram_user AS u, content AS c, post_dislikes AS pd" +
            " WHERE u.user_type = 'INSTAGRAM_USER' AND u.id = pd.user_id AND c.id = pd.post_id AND c.id = :id", nativeQuery = true)
    List<NistagramUser> findDislikesForPost(long id);

    @Query(value = "SELECT DISTINCT u.id, u.user_type, u.username, u.enabled, u.last_password_reset_date, u.profile_picture," +
            " u.about, u.private_profile FROM nistagram_user AS u, user_close_friend AS ucf WHERE ucf.user_id = :id AND ucf.close_friend_id = u.id", nativeQuery = true)
    List<NistagramUser> getCloseFriends(Long id);
}
