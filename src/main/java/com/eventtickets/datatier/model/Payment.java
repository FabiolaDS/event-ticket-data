package com.eventtickets.datatier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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


}
