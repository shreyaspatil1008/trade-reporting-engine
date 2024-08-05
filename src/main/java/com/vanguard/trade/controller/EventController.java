package com.vanguard.trade.controller;

import com.vanguard.trade.model.Event;
import com.vanguard.trade.service.EventFileReaderService;
import com.vanguard.trade.service.EventFilterService;
import com.vanguard.trade.service.EventPersistenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventFileReaderService eventFileReaderService;

    @Autowired
    private EventPersistenceService eventPersistenceService;

    @Autowired
    private EventFilterService eventFilterService;

    @Operation(summary = "Get all filtered events", description = "Retrieve filtered events based on specified criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of events"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Event> getEvents() throws IOException {
        List<Event> eventList = eventFileReaderService.readFiles();
        eventPersistenceService.loadEvents(eventList);
        return eventFilterService.getFilteredEvents(eventList);
    }
}