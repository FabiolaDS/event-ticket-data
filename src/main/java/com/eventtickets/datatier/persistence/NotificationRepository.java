package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification ,Long> {
}
