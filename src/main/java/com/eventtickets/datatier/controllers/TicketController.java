package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.TicketDTO;
import com.eventtickets.datatier.controllers.DTO.UserDTO;
import com.eventtickets.datatier.model.CreditCard;
import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.model.Ticket;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.CreditCardRepository;
import com.eventtickets.datatier.persistence.EventRepository;
import com.eventtickets.datatier.persistence.TicketRepository;
import com.eventtickets.datatier.persistence.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    @NonNull
    private TicketRepository ticketRepository;
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private EventRepository eventRepository;
    @NonNull
    private CreditCardRepository creditCardRepository;

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO
    ) {
        if (!eventRepository.existsById(ticketDTO.getEventId())
                || !creditCardRepository.existsById(ticketDTO.getPaymentId())) {
            return ResponseEntity.notFound().build();

        }


        Optional<TicketDTO> dto = userRepository.findById(ticketDTO.getBuyerId())
                .map(buyer -> {

                    Ticket ticket = ticketRepository.save(toEntity(ticketDTO));
                    buyer.getTickets().add(ticket);
                    userRepository.save(buyer);

                    return ticket;
                }).map(this::toDTO);
        if (dto.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto.get());

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByUser(@PathVariable long userId) {
        return ResponseEntity.of(userRepository.findById(userId)
                .map(user -> user.getTickets()
                        .stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList())));
    }


    private TicketDTO toDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getTicketNr(),
                ticket.getEvent().getId(),
                ticket.getPayment().getId(),
                ticket.getBuyer().getId(),
                ticket.getTimeOfPurchase());
    }

    private Ticket toEntity(TicketDTO ticketDTO) {
        Event event = eventRepository.findById(ticketDTO.getEventId())
                .orElseThrow();
        CreditCard creditCard = creditCardRepository
                .findById(ticketDTO.getPaymentId()).orElseThrow();
        User buyer = userRepository.findById(ticketDTO.getBuyerId())
                .orElseThrow();

        return new Ticket(ticketDTO.getTicketNr(), event, creditCard, buyer, ticketDTO.getTimeOfPurchase());
    }

}
