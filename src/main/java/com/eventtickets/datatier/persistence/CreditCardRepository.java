package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {}
