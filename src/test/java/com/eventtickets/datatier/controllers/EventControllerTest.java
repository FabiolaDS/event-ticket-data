package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.CreateEventDTO;
import com.eventtickets.datatier.controllers.DTO.EventDTO;
import com.eventtickets.datatier.model.Event;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest
{

  @Autowired private MockMvc mvc;   // mock REST requests
  @Autowired private ObjectMapper mapper;
  @Autowired private UserRepository userRepository;

  @Test void testGetAllEventsAfter() throws Exception
  {
    User organizer = new User(null,UUID.randomUUID().toString(),"","",false,null,null);
    userRepository.save(organizer);
    // Create new Event
    LocalDateTime time = LocalDateTime.now();
    CreateEventDTO dto = new CreateEventDTO("random name", "description",
        "location", "thumbnail", 10, time.plusDays(1).truncatedTo(ChronoUnit.MINUTES),400,
        organizer.getId());

    EventDTO created = mapper.readValue(mvc.perform(
        post("/events")
            .contentType("application/json")
            .content(mapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(), EventDTO.class);

    // Get list of all events
    MvcResult response = mvc
        .perform(get("/events?after={datetime}", time))
        .andExpect(status().isOk())
        .andReturn();

    String json = response.getResponse().getContentAsString();
    List<EventDTO> result = mapper.readValue(json, new TypeReference<List<EventDTO>>() {});

    // Confirm returned list contains newly created event
    assertTrue(result.contains(created));
  }

  @Test void testAddEvent() throws Exception
  {
    User organizer = new User(null, UUID.randomUUID().toString(),"","",false,null,null);
    userRepository.save(organizer);
    CreateEventDTO dto = new CreateEventDTO("random name", "description",
        "location", "thumbnail", 10, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),460,organizer.getId());

    MvcResult response = mvc.perform(
        post("/events")
            .contentType("application/json")
            .content(mapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andReturn();

    String json = response.getResponse().getContentAsString();
    EventDTO result = mapper.readValue(json, EventDTO.class);

    assertNotNull(result.getId());
    assertEquals(dto.getName(), result.getName());
    assertEquals(dto.getDescription(), result.getDescription());
    assertEquals(dto.getLocation(), result.getLocation());
    assertEquals(dto.getThumbnail(), result.getThumbnail());
    assertEquals(dto.getAvailableTickets(), result.getAvailableTickets());
    assertEquals(dto.getTimeOfTheEvent(), result.getTimeOfTheEvent());
    assertEquals(dto.getTicketPrice(), result.getTicketPrice());
    assertEquals(dto.getOrganizerId(), result.getOrganizerId());

    assertFalse(result.isCancelled());
  }
}