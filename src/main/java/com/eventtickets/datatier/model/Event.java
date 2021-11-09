package com.eventtickets.datatier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event
{
  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String description;
  private String location;
  private String thumbnail;
  private int nrOfTickets;
  private boolean isCancelled;
  private LocalDateTime dateTime;
  private int bookedTickets;
  private double price;
}
