package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.Event;

import java.util.List;

public interface EventRepository
{
  List<Event> getAllEvents();
  Event getEventById(Long id);
  void addEvent(Event event);

}
