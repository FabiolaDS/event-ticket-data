package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.EventDTO;
import com.eventtickets.datatier.controllers.DTO.UserDTO;
import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.model.Ticket;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.CategoryRepository;
import com.eventtickets.datatier.persistence.EventRepository;
import com.eventtickets.datatier.persistence.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	@NonNull
	private CategoryRepository categoryRepository;

	@GetMapping
	public List<EventDTO> getAllEventsAfter(
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after) {
		return eventRepository.findByTimeOfTheEventAfter(after)
			.stream()
			.map(this::toDTO)
			.collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EventDTO addEvent(@RequestBody EventDTO eventDTO) {
		Event event = toEntity(eventDTO);

		User organizer = userRepository.findById(eventDTO.getOrganizerId())
			.orElseThrow();
		event.setOrganizer(organizer);

		return toDTO(eventRepository.save(event));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {

		//        Optional<Event> event = eventRepository.findById(id);
		//
		//        if(event.isPresent()) {
		//            return ResponseEntity.ok(toDTO(event.get()));
		//        }
		//
		//        return ResponseEntity
		//                .notFound()
		//                .build();
		//      Same as above but shorter
		return ResponseEntity.of(
			eventRepository.findById(id)
				.map(this::toDTO));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EventDTO> updateEvent(@PathVariable long id,
		@RequestBody EventDTO updated) {
		return ResponseEntity.of(eventRepository.findById(id)
			.map(event -> {

				if (updated.getName() != null) {
					event.setName(updated.getName());
				}
				if (updated.getDescription() != null) {
					event.setDescription(updated.getDescription());
				}
				if (updated.getLocation() != null) {
					event.setLocation(updated.getLocation());
				}
				if (updated.getThumbnail() != null) {
					event.setThumbnail(updated.getThumbnail());
				}
				if (updated.getAvailableTickets() != null) {
					event.setAvailableTickets(updated.getAvailableTickets());
				}
				if (updated.getIsCancelled() != null) {
					event.setCancelled(updated.getIsCancelled());
				}
				if (updated.getTimeOfTheEvent() != null) {
					event.setTimeOfTheEvent(updated.getTimeOfTheEvent());
				}
				if (updated.getTicketPrice() != null) {
					event.setTicketPrice(updated.getTicketPrice());
				}
				if (updated.getCategory() != null) {
					event.setCategory(
						categoryRepository.findByName(updated.getCategory())
							.orElseThrow());
				}
				return event;
			}).map(eventRepository::save)
			.map(this::toDTO));

	}

	@GetMapping("/byCategoryAndTime")
	public List<EventDTO> getByCategoryAndTimeOfTheEventAfter(
		@RequestParam long categoryId,
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime dateTime) {

		return eventRepository
			.findByCategoryIdAndTimeOfTheEventAfter(categoryId, dateTime)
			.stream()
			.map(this::toDTO) //do the conversion
			.collect(Collectors.toList());
	}

	@GetMapping("/byLocationAndTime")
	public List<EventDTO> getByLocationAndTimeOfTheEventAfter(
		@RequestParam String location,
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime dateTime) {

		return eventRepository
			.findByLocationAndTimeOfTheEventAfter(location, dateTime).stream()
			.map(this::toDTO).collect(
				Collectors.toList());
	}

	@GetMapping("/{eventId}/participants")
	public ResponseEntity<List<UserDTO>> getParticipants(
		@PathVariable long eventId) {
		return ResponseEntity.of(eventRepository.findById(eventId)
			.map(e -> e.getBookedTickets().stream()
				.map(Ticket::getBuyer)
				.distinct()
				.map(entity -> new UserDTO(entity.getId(),
					entity.getEmail(),
					entity.getFullName(),
					entity.getPassword(),
					entity.getIsAdmin()))
				.collect(Collectors.toList())));

	}

	@GetMapping("/byName")
	public ResponseEntity<EventDTO> getEventByName(@RequestParam String name) {
		return ResponseEntity.of(eventRepository.findByName(name)// optional
			.map(this::toDTO));
	}

	private Event toEntity(EventDTO eventDTO) {
		return new Event(eventDTO.getId(),
			eventDTO.getName(),
			eventDTO.getDescription(),
			eventDTO.getLocation(),
			eventDTO.getAddress(),
			eventDTO.getThumbnail(),
			eventDTO.getAvailableTickets(),
			eventDTO.getIsCancelled(),
			eventDTO.getTimeOfTheEvent(),
			eventDTO.getTicketPrice(),
			categoryRepository.findByName(eventDTO.getCategory()).orElseThrow(),
			userRepository.getById(eventDTO.getOrganizerId()),
			new ArrayList<>());
	}

	private EventDTO toDTO(Event event) {
		return new EventDTO(event.getId(),
			event.getName(),
			event.getDescription(),
			event.getLocation(),
			event.getAddress(),
			event.getThumbnail(),
			event.getAvailableTickets(),
			event.isCancelled(),
			event.getTimeOfTheEvent(),
			event.getTicketPrice(),
			event.getCategory().getName(),
			event.getOrganizer().getId(),
			event.getBookedTickets().size());
	}
}
