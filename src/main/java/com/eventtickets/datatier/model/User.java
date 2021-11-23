package com.eventtickets.datatier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "account",
    uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String fullName;
    private String password;
    @JsonProperty("admin")
    private Boolean isAdmin = false;
    @ManyToMany
    private List<CreditCard> creditCards;
    @OneToMany
    private List<Ticket> tickets;
}