package com.vanguard.trade.service.filter.rules.executor;

import com.vanguard.trade.model.Event;

import java.io.FileNotFoundException;
import java.util.List;

public interface FilterRuleExecutor<Object, Boolean> {
    void initRuleConfig(String absolutePath) throws FileNotFoundException;

    List<Event> executeRules(List<Event> allEvents);
}
