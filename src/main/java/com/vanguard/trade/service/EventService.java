package com.vanguard.trade.service;

import com.vanguard.trade.model.Event;
import com.vanguard.trade.repository.EventRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @PostConstruct
    public void loadEvents() {
        // Load events from XML files and save to database
    }

    public List<Event> getFilteredEvents() {
        List<Event> allEvents = eventRepository.findAll();

        return allEvents.stream()
                .filter(event -> (
                        (event.getSellerParty().equals("EMU_BANK") && "AUD".equals(event.getPremiumCurrency())) ||
                                (event.getSellerParty().equals("BISON_BANK") && "USD".equals(event.getPremiumCurrency()))
                ) && !isAnagram(event.getSellerParty(), event.getBuyerParty()))
                .collect(Collectors.toList());
    }

    private boolean isAnagram(String str1, String str2) {
        // Method to check if two strings are anagrams
        return false;
    }
}

