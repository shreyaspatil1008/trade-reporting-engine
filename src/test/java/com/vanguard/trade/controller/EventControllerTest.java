package com.vanguard.trade.controller;

import com.vanguard.trade.service.EventFileReaderService;
import com.vanguard.trade.service.EventFilterService;
import com.vanguard.trade.service.EventPersistenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventFileReaderService eventFileReaderService;

    @MockBean
    private EventPersistenceService eventPersistenceService;

    @MockBean
    private EventFilterService eventFilterService;

    @Test
    public void testGetEvents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/events")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:password".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void testEvents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/events")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}