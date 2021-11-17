package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Ticket.TicketId>
{
  List<Ticket> findByBuyerId(Long userId);


}
