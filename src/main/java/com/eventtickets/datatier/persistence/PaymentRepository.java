package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.Payment;
import com.eventtickets.datatier.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Ticket.TicketId>
{

}
