package com.eventtickets.datatier.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO
{
  private long eventId;
  private String ticketNr;
  private String cardNumber;
}
