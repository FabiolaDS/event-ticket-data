package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.CreateEventDTO;
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
	@NonNull private EventRepository eventRepository;
	@NonNull private UserRepository userRepository;

	@GetMapping
	public List<EventDTO> getAllEventsAfter(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after) {
		return eventRepository.findByTimeOfTheEventAfter(after)
			.stream()
			.map(this::toDTO)
			.collect(Collectors.toList());
	}

	@PostMapping
	public EventDTO addEvent(@RequestBody CreateEventDTO eventDTO) {
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

	@GetMapping("/{id}") public EventDTO getEventById(@PathVariable Long id) {
		Event event = eventRepository.findById(id).orElseThrow();
		return toDTO(event);

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
