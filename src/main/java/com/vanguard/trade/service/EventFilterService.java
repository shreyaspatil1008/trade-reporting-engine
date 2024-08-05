package com.vanguard.trade.service;

import com.vanguard.trade.model.Event;
import com.vanguard.trade.service.filter.rules.executor.FilterRuleExecutor;
import com.vanguard.trade.service.filter.rules.executor.PredicateFilterRuleExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class EventFilterService {

    @Autowired
    private ApplicationContext ctx;

    public List<Event> getFilteredEvents(List<Event> allEvents) throws IOException {
        Resource resource = ctx.getResource("classpath:rules.csv");
        File ruleFile = resource.getFile();
        FilterRuleExecutor<Object, Boolean> filterRuleExecutor = new PredicateFilterRuleExecutor();
        filterRuleExecutor.initRuleConfig(ruleFile.getAbsolutePath());
        return filterRuleExecutor.executeRules(allEvents);

        /*return allEvents.stream()
                .filter(event -> (
                        (event.getSellerParty().equals("EMU_BANK") && "AUD".equals(event.getPremiumCurrency())) ||
                                (event.getSellerParty().equals("BISON_BANK") && "USD".equals(event.getPremiumCurrency()))
                ) && !isAnagram(event.getSellerParty(), event.getBuyerParty()))
                .collect(Collectors.toList());*/
    }

    /*private boolean isAnagram(String str1, String str2) {
        return str1.chars().mapToObj(c -> (char) c).sorted().toList()
                .equals(str2.chars().mapToObj(c -> (char) c).sorted().collect(Collectors.toList()));
    }*/
}

