package com.eventtickets.datatier.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

	private Long id;

	private String name;
	private String description;
	private String location;
	private String thumbnail;
	private Integer availableTickets;
	private Boolean isCancelled;
	private LocalDateTime timeOfTheEvent;
	private Double ticketPrice;
	private long organizerId;
	private int bookedTickets;
}
