package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.EventDTO;
import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.EventRepository;
import com.eventtickets.datatier.persistence.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    @NonNull
    private EventRepository eventRepository;
    @NonNull
    private UserRepository userRepository;

    @GetMapping
    public List<EventDTO> getAllEventsAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after) {
        return eventRepository.findByTimeOfTheEventAfter(after)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public EventDTO addEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setThumbnail(eventDTO.getThumbnail());
        event.setTimeOfTheEvent(eventDTO.getTimeOfTheEvent());
        event.setAvailableTickets(eventDTO.getAvailableTickets());
        event.setTicketPrice(eventDTO.getTicketPrice());

        User organizer = userRepository.findById(eventDTO.getOrganizerId())
                .orElseThrow();
        event.setOrganizer(organizer);

        return toDTO(eventRepository.save(event));
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        return toDTO(event);
    }

    @PatchMapping("/{id}")
    public EventDTO updateEvent(@PathVariable long id, @RequestBody EventDTO updated) {
        Event event = eventRepository.findById(id).orElseThrow();
        if (updated.getName()!= null){
            event.setName(updated.getName());
        }
        if(updated.getDescription() != null){
            event.setDescription(updated.getDescription());
        }


        if(updated.getLocation() != null){
            event.setLocation(updated.getLocation());
        }
        if(updated.getThumbnail() != null){
            event.setThumbnail(updated.getThumbnail());
        }
        if(updated.getAvailableTickets() != null){
            event.setAvailableTickets(updated.getAvailableTickets());
        }

        if(updated.getIsCancelled() != null){
            event.setCancelled(updated.getIsCancelled());
        }

        if(updated.getTimeOfTheEvent() != null){
            event.setTimeOfTheEvent(updated.getTimeOfTheEvent());
        }
        if(updated.getTicketPrice() != null){
            event.setTicketPrice(updated.getTicketPrice());
        }
        return toDTO(eventRepository.save(event));

    }

    private Event toEntity(EventDTO eventDTO) {
        return new Event(eventDTO.getId(),
                eventDTO.getName(),
                eventDTO.getDescription(),
                eventDTO.getLocation(),
                eventDTO.getThumbnail(),
                eventDTO.getAvailableTickets(),
                eventDTO.getIsCancelled(),
                eventDTO.getTimeOfTheEvent(),
                eventDTO.getTicketPrice(),
                userRepository.getById(eventDTO.getOrganizerId()),
                null);
    }

    private EventDTO toDTO(Event event) {
        return new EventDTO(event.getId(),
                event.getName(),
                event.getDescription(),
                event.getLocation(),
                event.getThumbnail(),
                event.getAvailableTickets(),
                event.isCancelled(),
                event.getTimeOfTheEvent(),
                event.getTicketPrice(),
                event.getOrganizer().getId(),
                event.getBookedTickets().size());
    }
}
