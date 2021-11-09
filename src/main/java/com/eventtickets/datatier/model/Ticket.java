package com.eventtickets.datatier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Ticket.TicketId.class)
public class Ticket
{
  public static class TicketId implements Serializable
  {
    private Long buyer;
    private Long event;
  }


  @Id
  @ManyToOne
  private User buyer;

  @Id
  @ManyToOne
  private Event event;

  private String ticketNr;
  private double price;
  private int nrOfTickets;

}
