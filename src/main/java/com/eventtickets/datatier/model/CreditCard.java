package com.eventtickets.datatier.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "cardNumber"))
@Data
@NoArgsConstructor
public class CreditCard
{
  @Id
  @GeneratedValue
  private Long id;

  private String cardNumber;
  private int expiryMonth;
  private int expiryYear;
  private int cvv;
  private String cardOwnerName;

  public CreditCard(String cardNumber, int expiryMonth, int expiryYear, int cvv,
      String cardOwnerName)
  {
    this.cardNumber = cardNumber;
    this.expiryMonth = expiryMonth;
    this.expiryYear = expiryYear;
    this.cvv = cvv;
    this.cardOwnerName = cardOwnerName;
  }

}
