package com.vanguard.trade.repository;

import com.vanguard.trade.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}

