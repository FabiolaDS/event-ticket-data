package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.persistence.EventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController
{
  private EventRepository eventRepository;
  public EventController(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }
@GetMapping("/events")
 public List<Event> getAllEvents() {

    return  eventRepository.getAllEvents();
  }
}
