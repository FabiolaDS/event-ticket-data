package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.PaymentDTO;
import com.eventtickets.datatier.model.CreditCard;
import com.eventtickets.datatier.model.Payment;
import com.eventtickets.datatier.model.Ticket;
import com.eventtickets.datatier.persistence.CreditCardRepository;
import com.eventtickets.datatier.persistence.PaymentRepository;
import com.eventtickets.datatier.persistence.TicketRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController
{
  @NonNull
  private PaymentRepository paymentRepository;
  @NonNull
  private CreditCardRepository creditCardRepository;
  @NonNull
  private TicketRepository ticketRepository;

  @PostMapping
  public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO)
  {
    Payment payment = paymentRepository.save(toEntity(paymentDTO));
    return toDTO(payment);
  }

  private PaymentDTO toDTO(Payment payment)
  {
    return new PaymentDTO(payment.getCreditCard().getCardNumber(),
        payment.getDateTime(), payment.getAmount(),
        payment.getTicketId().getBuyer(), payment.getTicketId().getEvent());

  }

  private Payment toEntity(PaymentDTO paymentDTO)
  {
    CreditCard creditCard = creditCardRepository
        .getById(paymentDTO.getCreditCardNumber());
    Ticket ticket = ticketRepository.getById(
        new Ticket.TicketId(paymentDTO.getBuyer(), paymentDTO.getEvent()));
    return new Payment(creditCard, paymentDTO.getDateTime(),
        paymentDTO.getAmount(), ticket);
  }
}
