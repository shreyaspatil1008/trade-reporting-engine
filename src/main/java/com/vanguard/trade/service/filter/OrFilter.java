package com.vanguard.trade.service.filter;

import com.vanguard.trade.exception.InvalidFilterUsageException;
import com.vanguard.trade.model.Event;
import com.vanguard.trade.service.filter.rules.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OrFilter implements Filter {
    private final Filter filter;
    private final Filter anotherFilter;
    private final List<Event> allEvents;
    private final List<Rule> rules;
    private final boolean isSecondaryAnd;
    private final boolean isSecondaryOr;
    Logger logger = LoggerFactory.getLogger(OrFilter.class);

    public OrFilter(Filter filter, Filter anotherFilter, List<Event> eventList, List<Rule> rules) {
        if (null == filter || null == anotherFilter) {
            logger.info("Invalid Filter Usage.");
            throw new InvalidFilterUsageException("Invalid Filter Usage.");
        }
        this.filter = filter;
        this.anotherFilter = anotherFilter;
        this.allEvents = eventList;
        this.rules = rules;
        isSecondaryAnd = rules.get(0).isSecondaryConditionAnd();
        isSecondaryOr = rules.get(0).isSecondaryConditionOr();
    }

    @Override
    public boolean isSecondaryAnd() {
        return isSecondaryAnd;
    }

    @Override
    public boolean isSecondaryOr() {
        return isSecondaryOr;
    }

    @Override
    public List<Event> apply(List<Event> eventList) {
        List<Event> firstFilteredEvents = filter.apply(eventList);
        List<Event> secondFilterEvents = anotherFilter.apply(eventList);
        List<Event> orFilteredEmployees = new ArrayList<>(firstFilteredEvents);
        secondFilterEvents.removeAll(firstFilteredEvents);
        orFilteredEmployees.addAll(secondFilterEvents);
        return orFilteredEmployees;
    }

    @Override
    public String toString() {
        return "OrFilter{" +
                "filter=" + filter +
                ", anotherFilter=" + anotherFilter +
                ", rules=" + rules +
                ", isSecondaryAnd=" + isSecondaryAnd +
                ", isSecondaryOr=" + isSecondaryOr +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
