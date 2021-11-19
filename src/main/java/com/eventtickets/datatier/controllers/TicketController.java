package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.TicketDTO;
import com.eventtickets.datatier.model.CreditCard;
import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.model.Ticket;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.CreditCardRepository;
import com.eventtickets.datatier.persistence.EventRepository;
import com.eventtickets.datatier.persistence.TicketRepository;
import com.eventtickets.datatier.persistence.UserRepository;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
public class TicketController
{
 @NonNull private TicketRepository ticketRepository;
 @NonNull private UserRepository userRepository;
 @NonNull private EventRepository eventRepository;
@NonNull private CreditCardRepository creditCardRepository;

  @PostMapping
  public TicketDTO createTicket(@RequestBody TicketDTO ticketDTO)
  {
    Ticket ticket = ticketRepository.save(toEntity(ticketDTO));

    return toDTO(ticket);
  }

  @GetMapping("/byUser/{userId}")
  public List<TicketDTO> getTicketsByUser(@PathVariable Long userId)
  {
    return userRepository.findById(userId).orElseThrow().getTickets().stream().map(this::toDTO)
        .collect(Collectors.toList());
  }


  private TicketDTO toDTO(Ticket ticket)
  {
    return new TicketDTO( ticket.getEvent().getId(),
        ticket.getTicketNr(), ticket.getPayment().getCardNumber());

  }

  private Ticket toEntity(TicketDTO ticketDTO)
  {
    Event event = eventRepository.findById(ticketDTO.getEventId())
        .orElseThrow();
    CreditCard creditCard = creditCardRepository.findById(ticketDTO.getCardNumber()).orElseThrow();
    return new Ticket(ticketDTO.getTicketNr(), event, creditCard);
  }

}
