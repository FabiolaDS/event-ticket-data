package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.CreateEventDTO;
import com.eventtickets.datatier.model.Event;
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

  @Test void getAllEvents() throws Exception
  {
    // Create new Event
    CreateEventDTO dto = new CreateEventDTO("random name", "description",
        "location", "thumbnail", 10, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),400);

    Event created = mapper.readValue(mvc.perform(
        post("/events")
            .contentType("application/json")
            .content(mapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(), Event.class);

    // Get list of all events
    MvcResult response = mvc.perform(get("/events")).andExpect(status().isOk())
        .andReturn();

    String json = response.getResponse().getContentAsString();
    List<Event> result = mapper.readValue(json, new TypeReference<List<Event>>() {});

    // Confirm returned list contains newly created event
    assertTrue(result.contains(created));
  }

  @Test void testAddEvent() throws Exception
  {
    CreateEventDTO dto = new CreateEventDTO("random name", "description",
        "location", "thumbnail", 10, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),460);

    MvcResult response = mvc.perform(
        post("/events")
            .contentType("application/json")
            .content(mapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andReturn();

    String json = response.getResponse().getContentAsString();
    Event result = mapper.readValue(json, Event.class);

    assertNotNull(result.getId());
    assertEquals(dto.getName(), result.getName());
    assertEquals(dto.getDescription(), result.getDescription());
    assertEquals(dto.getLocation(), result.getLocation());
    assertEquals(dto.getThumbnail(), result.getThumbnail());
    assertEquals(dto.getNrOfTickets(), result.getNrOfTickets());
    assertEquals(dto.getDateTime(), result.getDateTime());

    assertFalse(result.isCancelled());
  }
}