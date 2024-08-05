package com.vanguard.trade.service.filter;

import com.vanguard.trade.exception.InvalidFilterUsageException;
import com.vanguard.trade.model.Event;
import com.vanguard.trade.service.filter.rules.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AndFilter implements Filter {
    private final Filter filter;
    private final Filter anotherFilter;
    private final List<Event> allEvents;
    private final List<Rule> rules;
    private final boolean isSecondaryAnd;
    private final boolean isSecondaryOr;
    Logger logger = LoggerFactory.getLogger(OrFilter.class);

    public AndFilter(Filter filter, Filter anotherFilter, List<Event> eventList, List<Rule> rules) {
        if (null == filter || null == anotherFilter) {
            logger.info("Invalid Filter Usage.");
            throw new InvalidFilterUsageException("Invalid Filter Usage.");
        }
        this.filter = filter;
        this.anotherFilter = anotherFilter;
        this.allEvents = eventList;
        this.rules = rules;
        Rule rule = rules.get(0);
        isSecondaryAnd = rule.isSecondaryConditionAnd();
        isSecondaryOr = rule.isSecondaryConditionOr();
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
    public List<Event> apply(List<Event> filteredEventList) {
        List<Event> firstFilteredEvents = filter.apply(this.allEvents);
        List<Event> secondFilterEvents = anotherFilter.apply(firstFilteredEvents);
        logger.info("secondFilterEvents: {}; anotherFilter: {} ; firstFilteredEvents: {}", secondFilterEvents, anotherFilter, firstFilteredEvents);
        return secondFilterEvents;
    }

    @Override
    public String toString() {
        return "AndFilter{" +
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
