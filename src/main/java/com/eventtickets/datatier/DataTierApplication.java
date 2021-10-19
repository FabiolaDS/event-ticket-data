package com.eventtickets.datatier;

import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.persistence.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication public class DataTierApplication {

  public static void main(String[] args) {
    SpringApplication.run(DataTierApplication.class, args);
  }
}
