package com.vanguard.trade.service.filter;

import com.vanguard.trade.exception.InvalidFilterUsageException;
import com.vanguard.trade.model.Event;
import com.vanguard.trade.service.filter.rules.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FieldValueFilter implements Filter {

    private final List<Event> allEvents;
    private final List<Rule> rules;
    private final boolean isPrimaryAnd;
    private final boolean isPrimaryOr;
    private final boolean isSecondaryAnd;
    private final boolean isSecondaryOr;
    Logger logger = LoggerFactory.getLogger(FieldValueFilter.class);

    public FieldValueFilter(List<Event> allEvents, List<Rule> rules) {
        this.allEvents = allEvents;
        this.rules = rules;
        Rule rule = rules.get(0);
        isSecondaryAnd = rule.isSecondaryConditionAnd();
        isSecondaryOr = rule.isSecondaryConditionOr();
        isPrimaryAnd = rule.isPrimaryConditionAnd();
        isPrimaryOr = rule.isPrimaryConditionOr();
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
        if (rules.size() != 1) {
            logger.error("Invalid Filter Usage");
            throw new InvalidFilterUsageException("Invalid Filter Usage");
        }
        Rule rule = rules.get(0);
        return eventList.stream()
                .filter(event -> isMatch(event, rule))
                .collect(Collectors.toList());
    }

    private boolean isMatch(Event event, Rule rule) {
        String str1 = "";
        if (rule.getField().equals("buyer_party")) {
            str1 = event.getBuyerParty();
        }

        if (rule.getField().equals("seller_party")) {
            str1 = event.getSellerParty();
        }

        return str1.equals(rule.getValue());
    }

    @Override
    public String toString() {
        return "FieldValueFilter{" +
                ", rules=" + rules +
                ", isPrimaryAnd=" + isPrimaryAnd +
                ", isPrimaryOr=" + isPrimaryOr +
                ", isSecondaryAnd=" + isSecondaryAnd +
                ", isSecondaryOr=" + isSecondaryOr +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
