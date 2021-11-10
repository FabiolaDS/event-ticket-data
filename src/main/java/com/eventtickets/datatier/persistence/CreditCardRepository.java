package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard,String>
{
  List<CreditCard> findByOwnerId(long ownerId);

}
