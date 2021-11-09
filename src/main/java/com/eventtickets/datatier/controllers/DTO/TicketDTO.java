package com.eventtickets.datatier.controllers.DTO;

import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO
{
  private long buyerId;
  private long eventId;
  private String ticketNr;
  private double price;
  private int nrOfTickets;
}
