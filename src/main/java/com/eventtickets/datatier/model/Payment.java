package com.eventtickets.datatier.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor

public class Payment
{
  @ManyToOne
  private CreditCard creditCard;
  private LocalDateTime dateTime;
  private double amount;
  @OneToOne
  @MapsId
  private Ticket ticket;
  @Id
  private Ticket.TicketId ticketId;

  public Payment(CreditCard creditCard, LocalDateTime localDateTime,
      double amount, Ticket ticket)
  {
    this.creditCard = creditCard;
    this.dateTime = localDateTime;
    this.amount = amount;
    this.ticket = ticket;
  }

}
