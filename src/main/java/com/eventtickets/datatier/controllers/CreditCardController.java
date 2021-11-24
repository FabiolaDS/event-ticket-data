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
public class CreditCardController {
	@NonNull private CreditCardRepository creditCardRepository;
	@NonNull private UserRepository userRepository;

	@PostMapping("/{userId}")
	public CreditCardDTO createCreditCard(
		@RequestBody CreditCardDTO creditCardDTO, @PathVariable long userId) {
		User user = userRepository.findById(userId).orElseThrow();

		CreditCard creditCard = creditCardRepository
			.save(toEntity(creditCardDTO));
		user.getCreditCards().add(creditCard);
		userRepository.save(user);
		return toDTO(creditCard);
	}

	@GetMapping("/user/{userId}")
	public List<CreditCardDTO> getAllCreditCardsForUser(
		@PathVariable long userId) {
		return userRepository.findById(userId).orElseThrow().getCreditCards()
			.stream().map(this::toDTO).collect(Collectors.toList());

	}

	@DeleteMapping("/{cardId}")
	public void removeCreditCard(@PathVariable long cardId) {
		creditCardRepository.deleteById(cardId);
	}

	private CreditCardDTO toDTO(CreditCard entity) {
		return new CreditCardDTO(entity.getId(), entity.getCardNumber(),
			entity.getExpiryMonth(), entity.getExpiryYear(), entity.getCvv(),
			entity.getCardOwnerName());

	}

	private CreditCard toEntity(CreditCardDTO dto) {
		CreditCard creditCard = new CreditCard();
		creditCard.setId(dto.getId());
		creditCard.setCardNumber(dto.getCardNumber());
		creditCard.setCardOwnerName(dto.getCardOwnerName());
		creditCard.setCvv(dto.getCvv());
		creditCard.setExpiryMonth(dto.getExpiryMonth());
		creditCard.setExpiryYear(dto.getExpiryYear());

		return creditCard;
	}

}
