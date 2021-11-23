package com.eventtickets.datatier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
  private int availableTickets;
  private boolean isCancelled;
  private LocalDateTime timeOfTheEvent;
  private double ticketPrice;
  @ManyToOne
  private User organizer;
  @OneToMany(mappedBy = "event")
  private List<Ticket>  bookedTickets = new ArrayList<>();


}
