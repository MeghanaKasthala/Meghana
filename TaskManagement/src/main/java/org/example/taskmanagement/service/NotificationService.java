package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.Notification;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void notify(User user, String message) {

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);

        notificationRepository.save(notification);
    }
}
