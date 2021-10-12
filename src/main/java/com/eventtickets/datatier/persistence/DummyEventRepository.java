package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.Event;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DummyEventRepository implements EventRepository
{
  private List<Event> events;
 public DummyEventRepository() {
    this.events = new ArrayList<>();
    for(Long i = 0l; i < 5l; i++) {
      Event event1 = new Event(i, "Name", "Description", "Location", "url", 10, false, LocalDateTime.now());
      events.add(event1);

    }
  }

  @Override public List<Event> getAllEvents()
  {
    return events;
  }

  @Override public Event getEventById(Long id)
  {
    for(int i = 0; i< events.size(); i++) {
      if(events.get(i).getId() == id)

      {
        return events.get(i);
      }
    }
     throw new IllegalArgumentException("No id") ;
  }

  @Override public void addEvent(Event event)
  {
    events.add(event);

  }
}
