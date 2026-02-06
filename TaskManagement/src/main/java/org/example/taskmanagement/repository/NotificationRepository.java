package org.example.taskmanagement.repository;

import org.example.taskmanagement.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
