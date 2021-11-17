package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.TicketDTO;
import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.model.Ticket;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.EventRepository;
import com.eventtickets.datatier.persistence.TicketRepository;
import com.eventtickets.datatier.persistence.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
public class TicketController
{
  private TicketRepository ticketRepository;
  private UserRepository userRepository;
  private EventRepository eventRepository;

  public TicketController(TicketRepository ticketRepository,
      UserRepository userRepository, EventRepository eventRepository)
  {
    this.ticketRepository = ticketRepository;
    this.userRepository = userRepository;
    this.eventRepository = eventRepository;
  }

  @PostMapping
  public TicketDTO createTicket(@RequestBody TicketDTO ticketDTO)
  {
    Ticket ticket = ticketRepository.save(toEntity(ticketDTO));

    return toDTO(ticket);
  }

  @GetMapping("/byUser/{userId}")
  public List<TicketDTO> getTicketsByUser(@PathVariable Long userId)
  {
    return ticketRepository.findByBuyerId(userId).stream().map(this::toDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/byUser/{buyerId}/{eventId}")
  public TicketDTO getById(long buyerId, long eventId)
  {
    return toDTO(
        ticketRepository.getById(new Ticket.TicketId(buyerId, eventId)));
  }

  private TicketDTO toDTO(Ticket ticket)
  {
    return new TicketDTO(ticket.getBuyer().getId(), ticket.getEvent().getId(),
        ticket.getTicketNr(), ticket.getPrice(), ticket.getNrOfTickets());

  }

  private Ticket toEntity(TicketDTO ticketDTO)
  {
    User user = userRepository.findById(ticketDTO.getBuyerId()).orElseThrow();
    Event event = eventRepository.findById(ticketDTO.getEventId())
        .orElseThrow();
    return new Ticket(user, event, ticketDTO.getTicketNr(),
        ticketDTO.getPrice(), ticketDTO.getNrOfTickets());
  }

}
