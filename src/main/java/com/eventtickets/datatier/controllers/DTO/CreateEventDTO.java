package com.eventtickets.datatier.controllers.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class CreateEventDTO
{
  private String name;
  private String description;
  private String location;
  private String thumbnail;
  private int nrOfTickets;
  private LocalDateTime dateTime;
}
