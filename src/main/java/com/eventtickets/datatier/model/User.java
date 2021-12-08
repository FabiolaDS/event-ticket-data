package com.eventtickets.datatier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "account",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
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
    private Boolean isAdmin = false;

    @ManyToMany
    private List<CreditCard> creditCards;

    @OneToMany(mappedBy = "buyer")
    private List<Ticket> tickets;
    @ManyToMany
    private List<Notification> notifications;
}