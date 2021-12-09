package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.EventDTO;
import com.eventtickets.datatier.controllers.DTO.TicketDTO;
import com.eventtickets.datatier.model.*;
import com.eventtickets.datatier.persistence.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private TicketRepository ticketRepository;


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

    private TicketDTO randTicketDTO(long buyerId, long event, long paymentId) {
        return new TicketDTO(
                randStr(),
                event,
                paymentId,
                buyerId,
                LocalDateTime.now()
        );
    }

    private Ticket randTicket(Event event, CreditCard payment, User buyer) {
        return new Ticket(
                randStr(),
                event,
                payment,
                buyer,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)


        );
    }

    private Event randEvent(Category category, User organizer) {
        return new Event(
                null,
                randStr(),
                randStr(),
                randStr(),
                randStr(),
                randStr(),
                (int) (Math.random() * 100),
                false,
                LocalDateTime.now(),
                Math.random() * 100,
                category,
                organizer,
                new ArrayList<>()

        );


    }

    private Category randCategory() {
        return new Category(null, randStr());
    }

    private CreditCard randCreditCard() {
        return new CreditCard(
                randStr(),
                (int) (Math.random() * 11 + 1),
                2000,
                (int) (Math.random() * 999),
                randStr()


        );

    }


    @Test
    void testCreateTicket() throws Exception {
        User buyer = randUser();
        userRepository.save(buyer);
        Category cat = categoryRepository.save(randCategory());

        User organizer = randUser();
        userRepository.save(organizer);

        Event e = randEvent(cat, organizer);
        eventRepository.save(e);

        CreditCard c = randCreditCard();
        creditCardRepository.save(c);


        TicketDTO dto = new TicketDTO(
                randStr(),
                e.getId(),
                c.getId(),
                buyer.getId(),
                LocalDateTime.now()

        );


        MvcResult response = mvc.perform(
                        post("/tickets")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        String json = response.getResponse().getContentAsString();
        TicketDTO result = mapper.readValue(json, TicketDTO.class);

        assertEquals(dto.getTicketNr(), result.getTicketNr());
        assertEquals(dto.getBuyerId(), result.getBuyerId());
        assertEquals(dto.getEventId(), result.getEventId());
        assertEquals(dto.getPaymentId(), result.getPaymentId());
        assertEquals(dto.getTimeOfPurchase(), result.getTimeOfPurchase());


    }

    @Test
    void testCreateTicket_invalidBuyer() throws Exception {
        Category cat = categoryRepository.save(randCategory());

        User organizer = randUser();
        userRepository.save(organizer);

        Event e = randEvent(cat, organizer);
        eventRepository.save(e);

        CreditCard c = randCreditCard();
        creditCardRepository.save(c);


        TicketDTO dto = new TicketDTO(
                randStr(),
                e.getId(),
                c.getId(),
                -1,
                LocalDateTime.now()

        );


        mvc.perform(
                        post("/tickets")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());

    }


    @Test
    void testCreateTicket_invalidEvent() throws Exception {
        Category cat = categoryRepository.save(randCategory());

        User buyer = randUser();
        userRepository.save(buyer);

        User organizer = randUser();
        userRepository.save(organizer);


        CreditCard c = randCreditCard();
        creditCardRepository.save(c);


        TicketDTO dto = new TicketDTO(
                randStr(),
                -1,
                c.getId(),
                buyer.getId(),
                LocalDateTime.now()

        );


        mvc.perform(
                        post("/tickets")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());

    }

    @Test
    void testCreateTicket_invalidPayment() throws Exception {
        Category cat = categoryRepository.save(randCategory());

        User buyer = randUser();
        userRepository.save(buyer);

        User organizer = randUser();
        userRepository.save(organizer);

        Event e = randEvent(cat, organizer);
        eventRepository.save(e);


        TicketDTO dto = new TicketDTO(
                randStr(),
                e.getId(),
                -1,
                buyer.getId(),
                LocalDateTime.now()

        );



        mvc.perform(
                        post("/tickets")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());

    }

    @Test
    void testGetTicketForUser() throws Exception {
        User buyer = randUser();
        userRepository.save(buyer);
        User organizer = userRepository.save(randUser());
        Category cat = categoryRepository.save(randCategory());
        Event e = eventRepository.save(randEvent(cat, organizer));

        CreditCard c = creditCardRepository.save(randCreditCard());

        Ticket ticket = randTicket(
                e,
                c,
                buyer

        );
        ticketRepository.save(ticket);

        MvcResult result = mvc.perform(get("/tickets/byUser/{userId}", buyer.getId()))
                .andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        List<TicketDTO> tickets = mapper.readValue(json, new TypeReference<List<TicketDTO>>() {
        });


        TicketDTO ticketDTO = new TicketDTO(ticket.getTicketNr(), ticket.getEvent().getId(),
                ticket.getPayment().getId(),
                ticket.getBuyer().getId(),
                ticket.getTimeOfPurchase());
        assertTrue(tickets.contains(ticketDTO));
    }

    @Test
    void testGetTicketForUser_invalidUser() throws Exception {

        mvc.perform(get("/tickets/byUser/{userId}", -1))
                .andExpect(status().isNotFound());


    }
}
