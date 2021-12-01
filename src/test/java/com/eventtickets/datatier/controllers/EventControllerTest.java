package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.EventDTO;
import com.eventtickets.datatier.model.Category;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.CategoryRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mvc;   // mock REST requests
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository catRepo;

    private String randStr() {
        return UUID.randomUUID().toString();
    }

    private User randUser() {
        return new User(null,
                randStr(),
                randStr(),
                randStr(),
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    private EventDTO randEventDTO(String cat, long organizer) {
        return new EventDTO(
                null,
                randStr(),
                randStr(),
                randStr(),
                randStr(),
                (int) (Math.random() * 100),
                false,
                LocalDateTime.now(),
                Math.random() * 100,
                cat,
                organizer,
                (int) (Math.random() * 100)
        );
    }

    private Category randCategory() {
        return new Category(null, randStr());
    }

    @Test
    void testGetAllEventsAfter() throws Exception {
        User organizer = randUser();
        userRepository.save(organizer);

        Category cat = randCategory();
        catRepo.save(cat);

        LocalDateTime time = LocalDateTime.now();

        EventDTO dto = randEventDTO(cat.getName(), organizer.getId());
        dto.setTimeOfTheEvent(time);

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

        System.out.println("##################################");
        System.out.println(result);

        // Confirm returned list contains newly created event
        assertTrue(result.contains(created));
    }

    @Test
    void testAddEvent() throws Exception {
        User organizer = randUser();
        userRepository.save(organizer);

        Category cat = randCategory();
        catRepo.save(cat);

        EventDTO dto = randEventDTO(cat.getName(), organizer.getId());

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

        assertFalse(result.getIsCancelled());
    }
}