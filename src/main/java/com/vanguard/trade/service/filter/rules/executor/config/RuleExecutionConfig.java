package com.vanguard.trade.service.filter.rules.executor.config;

import com.vanguard.trade.service.filter.rules.Rule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RuleExecutionConfig {
    private final LinkedHashMap<Integer, List<Rule>> ruleNumberWiseRules;

    public RuleExecutionConfig(List<Rule> rules) {
        ruleNumberWiseRules = new LinkedHashMap<>();
        for (Rule rule : rules) {
            if (rule.isApplicable()) {
                List<Rule> ruleList = ruleNumberWiseRules.get(rule.getRuleNumber());
                if (ruleList == null) {
                    ruleList = new ArrayList<>();
                }
                ruleList.add(rule);
                ruleNumberWiseRules.put(rule.getRuleNumber(), ruleList);
            }
        }
    }

    public LinkedHashMap<Integer, List<Rule>> getRuleNumberWiseRules() {
        return ruleNumberWiseRules;
    }
}
