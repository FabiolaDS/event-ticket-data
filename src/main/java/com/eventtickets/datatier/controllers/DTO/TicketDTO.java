package com.eventtickets.datatier.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
	private String ticketNr;
	private long eventId;
	private long paymentId;
	private long buyerId;
	private LocalDateTime timeOfPurchase;

}
