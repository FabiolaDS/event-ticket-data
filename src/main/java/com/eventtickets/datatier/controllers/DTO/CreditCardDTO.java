package com.eventtickets.datatier.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDTO
{
  private String cardNumber;
  private int expiryMonth;
  private int expiryYear;
  private int cvv;
  private String cardOwnerName;
  private long ownerId;
}
