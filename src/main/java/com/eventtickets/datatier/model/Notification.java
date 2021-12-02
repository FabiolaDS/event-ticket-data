package com.eventtickets.datatier.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Notification implements Comparable<Notification>
{
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private  String message;
    private LocalDateTime timestamp;

    @Override
    public int compareTo(Notification o) {
        return o.timestamp.compareTo(timestamp);
    }
}
