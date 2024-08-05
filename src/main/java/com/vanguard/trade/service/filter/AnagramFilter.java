package com.vanguard.trade.service.filter;

import com.vanguard.trade.exception.InvalidConfigurationException;
import com.vanguard.trade.model.Event;
import com.vanguard.trade.service.filter.rules.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class AnagramFilter implements Filter {
    private final List<Event> allEvents;
    private final List<Rule> rules;
    private final boolean isPrimaryAnd;
    private final boolean isPrimaryOr;
    private final boolean isSecondaryAnd;
    private final boolean isSecondaryOr;
    Logger logger = LoggerFactory.getLogger(AnagramFilter.class);

    public AnagramFilter(List<Event> allEvents, List<Rule> rules) {
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
    public List<Event> apply(List<Event> filteredEvents) {
        Rule rule = rules.get(0);
        if (rule.isCustom() && !rule.isValueAnagram()) {
            logger.error("Invalid Configuration Provided");
            throw new InvalidConfigurationException("Invalid Configuration Provided.");
        }
        if (rule.isCustom() && rule.isValueAnagram()) {

            return filteredEvents.stream()
                    .filter(event -> !isAnagram(event, rule))
                    .collect(Collectors.toList());
        }
        return filteredEvents;
    }

    private boolean isAnagram(Event event, Rule rule) {
        String str1 = "";
        String str2 = "";
        if (rule.getField().equals("buyer_party")) {
            str1 = event.getBuyerParty();
        }

        if (rule.getField().equals("seller_party")) {
            str1 = event.getSellerParty();
        }

        if (rule.getPrimaryCondition().equals("buyer_party")) {
            str2 = event.getBuyerParty();
        }

        if (rule.getPrimaryCondition().equals("seller_party")) {
            str2 = event.getSellerParty();
        }

        return str1.chars().mapToObj(c -> (char) c).sorted().toList()
                .equals(str2.chars().mapToObj(c -> (char) c).sorted().collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "AnagramFilter{" +
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
