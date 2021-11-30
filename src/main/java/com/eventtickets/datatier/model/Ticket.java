package com.eventtickets.datatier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ticket
{
  @Id
  private String ticketNr;

  @ManyToOne
  private Event event;

  @ManyToOne
  private CreditCard payment;

}
