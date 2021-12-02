package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.model.Notification;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.NotificationRepository;
import com.eventtickets.datatier.persistence.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Notification createNotification(@RequestBody Notification notification) {

        return notificationRepository.save(notification);
    }

    @GetMapping("/byUser/{id}")
    public ResponseEntity<List<Notification>> getNotificationByUser(@PathVariable long id) {
        return ResponseEntity.of(userRepository.findById(id)
                .map(User::getNotifications)
                .map(ls -> {
                    Collections.sort(ls);
                    return ls;
                }));
    }

    @PostMapping("/byUser/{userId}")

    public ResponseEntity<Notification> addNotification(@RequestParam long notificationId, @PathVariable long userId) {
        return ResponseEntity.of(
                userRepository.findById(userId).map(user ->
                        notificationRepository.findById(notificationId).map(notification -> {

                            user.getNotifications().add(notification);
                            userRepository.save(user);
                            return notification;

                        }).orElse(null)
                ));
    }
}
