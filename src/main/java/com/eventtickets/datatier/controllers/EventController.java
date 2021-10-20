package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.CreateEventDTO;
import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.persistence.EventRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<Event> getAllEvents() {

        return eventRepository.findAll();
    }

    @PostMapping
    public Event addEvent(@Validated @RequestBody CreateEventDTO eventDTO)
    {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setThumbnail(eventDTO.getThumbnail());
        event.setNrOfTickets(eventDTO.getNrOfTickets());
        event.setDateTime(eventDTO.getDateTime());

        return eventRepository.save(event);
    }
}
