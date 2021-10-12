package com.eventtickets.datatier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event
{
  private Long id;
  private String name;
  private String description;
  private String location;
  private String thumbnail;
  private int nrOfTickets;
  private boolean isCancelled;
  private LocalDateTime dateTime;
}
