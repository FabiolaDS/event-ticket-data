package com.eventtickets.datatier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard
{
  @Id
  private int cardNumber;
  private int expiryMonth;
  private int expiryYear;
  private int cvv;
  private String cardOwnerName;
  @ManyToOne
  private User owner;

}
