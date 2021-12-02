package com.eventtickets.datatier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
