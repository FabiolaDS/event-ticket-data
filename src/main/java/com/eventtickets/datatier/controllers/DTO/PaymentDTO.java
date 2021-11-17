package com.eventtickets.datatier.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO
{
  private String creditCardNumber;
  private LocalDateTime dateTime;
  private double amount;
  private long buyer;
  private long event;
}
