package com.vanguard.trade.service.filter.rules.executor;

import com.opencsv.bean.CsvToBeanBuilder;
import com.vanguard.trade.exception.EventProcessingException;
import com.vanguard.trade.model.Event;
import com.vanguard.trade.service.filter.*;
import com.vanguard.trade.service.filter.rules.Rule;
import com.vanguard.trade.service.filter.rules.executor.config.RuleExecutionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PredicateFilterRuleExecutor implements FilterRuleExecutor<Object, Boolean> {
    Logger logger = LoggerFactory.getLogger(PredicateFilterRuleExecutor.class);

    private RuleExecutionConfig ruleExecutionConfig;

    private Filter getFilterByRule(List<Event> allEvents, List<Event> filteredEvents, Filter oldFilter, List<Rule> curRule) throws CloneNotSupportedException {

        AtomicReference<Filter> oldAtomicFilter = new AtomicReference<>();

        Filter tempFilter;
        if (curRule.get(0).isCustom()) {
            tempFilter = new AnagramFilter(filteredEvents, curRule);
        } else {
            tempFilter = new FieldValueFilter(allEvents, curRule);
        }
        if (null == oldFilter) {
            oldAtomicFilter.set((Filter) tempFilter.clone());
        } else {
            oldAtomicFilter.set(getAndOrOrFilter(allEvents, oldFilter, curRule, (Filter)tempFilter.clone()));
        }
        return (Filter)oldAtomicFilter.get().clone();
    }

    private Filter getAndOrOrFilter(List<Event> allEvents, Filter oldFilter, List<Rule> curRule, Filter tempFilter) throws CloneNotSupportedException {
        AtomicReference<Filter> atomicFilter = new AtomicReference<>();
        if (tempFilter.isSecondaryAnd()) {
            atomicFilter.set(new AndFilter(oldFilter, tempFilter, allEvents, curRule));
        } else if (tempFilter.isSecondaryOr()) {
            atomicFilter.set(new OrFilter(oldFilter, tempFilter, allEvents, curRule));
        }
        return (Filter)atomicFilter.get().clone();
    }

    @Override
    public void initRuleConfig(String ruleFile) throws FileNotFoundException {
        List<Rule> rules = new CsvToBeanBuilder<Rule>(new FileReader(ruleFile))
                .withType(Rule.class)
                .build()
                .parse();
        this.ruleExecutionConfig = new RuleExecutionConfig(rules);
    }

    @Override
    public List<Event> executeRules(List<Event> allEvents) {
        List<Event> filteredEvents = new ArrayList<>(allEvents);
        AtomicReference<Filter> oldFilter = new AtomicReference<>();
        ruleExecutionConfig.getRuleNumberWiseRules().forEach((key, value) -> {
            try {
                if (value.size() == 1) {
                    oldFilter.set(getFilterByRule(allEvents, filteredEvents, oldFilter.get(), List.of(value.get(0))));

                } else {
                    AtomicReference<Filter> localOldFilter = new AtomicReference<>();
                    for (Rule rule : value) {
                        localOldFilter.set(getFilterByRule(allEvents, filteredEvents, localOldFilter.get(), List.of(rule)));
                    }
                    if (null != oldFilter.get()) {
                        oldFilter.set(getAndOrOrFilter(allEvents, (Filter) oldFilter.get().clone(), value, (Filter) localOldFilter.get().clone()));
                    } else {
                        oldFilter.set((Filter)localOldFilter.get().clone());
                    }
                }

            } catch (CloneNotSupportedException e) {
                logger.error(e.getMessage());
                throw new EventProcessingException(e.getMessage());
            }
        });
        Filter filter = oldFilter.get();
        logger.info("executeRules filter: {}", filter);
        return filter.apply(allEvents);
    }
}
