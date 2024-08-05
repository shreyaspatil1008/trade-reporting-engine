package com.vanguard.trade.service;

import com.vanguard.trade.model.Event;
import com.vanguard.trade.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    public EventServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFilteredEvents() {
        Event event1 = new Event();
        event1.setSellerParty("EMU_BANK");
        event1.setPremiumCurrency("AUD");
        Event event2 = new Event();
        event2.setSellerParty("BISON_BANK");
        event2.setPremiumCurrency("USD");

        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        List<Event> filteredEvents = eventService.getFilteredEvents();
        assert (filteredEvents.size() == 2);
    }
}

