package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.CreditCardDTO;
import com.eventtickets.datatier.model.CreditCard;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.CreditCardRepository;
import com.eventtickets.datatier.persistence.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creditCards")
public class CreditCardController
{
  @NonNull private CreditCardRepository creditCardRepository;
  @NonNull private UserRepository userRepository;

  @PostMapping public CreditCardDTO createCreditCard(
      @RequestBody CreditCardDTO creditCardDTO)
  {
    return toDTO(creditCardRepository.save(toEntity(creditCardDTO)));
  }

  @GetMapping("/user/{userId}")
  public List<CreditCardDTO> getAllCreditCardsForUser(@PathVariable long userId)
  {
    return creditCardRepository.findByOwnerId(userId).stream().map(this::toDTO)
        .collect(Collectors.toList());

  }

  private CreditCardDTO toDTO(CreditCard entity)
  {
    return new CreditCardDTO(entity.getCardNumber(), entity.getExpiryMonth(),
        entity.getExpiryYear(), entity.getCvv(), entity.getCardOwnerName(),
        entity.getOwner().getId());

  }

  private CreditCard toEntity(CreditCardDTO dto)
  {
    CreditCard creditCard = new CreditCard();
    creditCard.setCardNumber(dto.getCardNumber());
    creditCard.setCardOwnerName(dto.getCardOwnerName());
    creditCard.setCvv(dto.getCvv());
    creditCard.setExpiryMonth(dto.getExpiryMonth());
    creditCard.setExpiryYear(dto.getExpiryYear());

    User userOwner = userRepository.findById(dto.getOwnerId()).orElseThrow();

    creditCard.setOwner(userOwner);
    return creditCard;
  }

}
