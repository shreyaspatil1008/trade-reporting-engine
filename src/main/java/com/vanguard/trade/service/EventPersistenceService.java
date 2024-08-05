package com.vanguard.trade.service;

import com.vanguard.trade.model.Event;
import com.vanguard.trade.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventPersistenceService {

    @Autowired
    private EventRepository eventRepository;

    public void loadEvents(List<Event> eventList) {
        eventRepository.saveAll(eventList);
    }
}

