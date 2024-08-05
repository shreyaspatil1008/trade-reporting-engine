package com.vanguard.trade.service.filter.rules;

import com.opencsv.bean.CsvBindByName;

public class Rule {
    @CsvBindByName(column = "RULE_NUMBER")
    private Integer ruleNumber;

    @CsvBindByName(column = "FIELD")
    private String field;

    @CsvBindByName(column = "VALUE")
    private String value;

    @CsvBindByName(column = "PRIMARY_CONDITION")
    private String primaryCondition;

    @CsvBindByName(column = "SECONDARY_CONDITION")
    private String secondaryCondition;

    @CsvBindByName(column = "IS_APPLICABLE")
    private String isApplicable;

    @CsvBindByName(column = "IS_CUSTOM")
    private String isCustom;

    public Integer getRuleNumber() {
        return ruleNumber;
    }

    public String getField() {
        return this.field;
    }

    public String getValue() {
        return value;
    }

    public String getPrimaryCondition() {
        return this.primaryCondition;
    }

    public boolean isPrimaryConditionAnd() {
        return primaryCondition.equals("AND");
    }

    public boolean isPrimaryConditionOr() {
        return primaryCondition.equals("OR");
    }

    public boolean isSecondaryCondition() {
        return !secondaryCondition.equals("NA");
    }

    public boolean isSecondaryConditionAnd() {
        return secondaryCondition.equals("AND");
    }

    public boolean isValueAnagram() {
        return value.equals("ANAGRAM");
    }

    public boolean isSecondaryConditionOr() {
        return secondaryCondition.equals("OR");
    }

    public boolean isApplicable() {
        return this.isApplicable.equals("YES");
    }

    public boolean isCustom() {
        return this.isCustom.equals("YES");
    }

    @Override
    public String toString() {
        return "Rule{" +
                "ruleNumber=" + ruleNumber +
                ", field='" + field + '\'' +
                ", value='" + value + '\'' +
                ", primaryCondition='" + primaryCondition + '\'' +
                ", secondaryCondition='" + secondaryCondition + '\'' +
                ", isApplicable='" + isApplicable + '\'' +
                ", isCustom='" + isCustom + '\'' +
                '}';
    }
}
