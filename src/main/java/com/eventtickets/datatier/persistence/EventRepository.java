package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>
{
  List<Event> findByTimeOfTheEventAfter(LocalDateTime localDateTime);
  List <Event> findByCategoryId(long categoryId);
}
