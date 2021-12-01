package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.model.Notification;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.NotificationRepository;
import com.eventtickets.datatier.persistence.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    @NonNull
    private NotificationRepository notificationRepository;
    @NonNull
    private UserRepository userRepository;

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {

        return notificationRepository.save(notification);
    }

    @GetMapping("/byUser/{id}")
    public List<Notification> getNotificationByUser(@PathVariable long id) {
        User user = userRepository.findById(id).orElseThrow();
        List<Notification> notifications = user.getNotifications();
        Collections.sort(notifications);
        return notifications;
    }

    @PostMapping("/byUser/{userId}")
    public Notification addNotification(@RequestParam long notificationId, @PathVariable long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();

        user.getNotifications().add(notification);
        userRepository.save(user);
        return notification;
    }


}
