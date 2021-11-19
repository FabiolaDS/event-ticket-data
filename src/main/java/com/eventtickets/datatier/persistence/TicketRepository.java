package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String>
{

}
