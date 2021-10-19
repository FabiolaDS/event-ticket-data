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

  @Bean
  public CommandLineRunner testRepo(EventRepository repo) {
		return arg -> {
		  LocalDateTime dateTime = LocalDateTime.now();

			Event e = new Event(null,
          "test event",
          "test event",
          "qwerty",
          "yes",
          6,
          false,
          dateTime);

      System.out.println(e);

      repo.save(e);

      System.out.println(e);

      System.out.println(repo.findByDateTimeBetween(
          dateTime.minusDays(1),
          dateTime.plusDays(1)));
    };
	}
}
