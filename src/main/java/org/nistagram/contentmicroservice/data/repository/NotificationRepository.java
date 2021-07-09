package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    @Query(value = "SELECT * FROM notification WHERE user_id = :id", nativeQuery = true)
    List<Notification> findByUser(Long id);
}
