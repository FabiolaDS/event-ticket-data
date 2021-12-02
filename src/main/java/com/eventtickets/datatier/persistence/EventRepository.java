package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByTimeOfTheEventAfter(LocalDateTime localDateTime);

    List<Event> findByCategoryIdAndTimeOfTheEventAfter(long categoryId, LocalDateTime timeOfTheEvent);

    Optional<Event> findByName(String name);
}
