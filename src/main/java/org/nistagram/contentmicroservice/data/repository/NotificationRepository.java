package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
