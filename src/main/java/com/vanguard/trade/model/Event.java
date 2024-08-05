package com.vanguard.trade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Event {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @JsonProperty("buyer_party")
    private String buyerParty;
    @JsonProperty("seller_party")
    private String sellerParty;
    @JsonProperty("premium_amount")
    private Double premiumAmount;
    @JsonProperty("premium_currency")
    private String premiumCurrency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyerParty() {
        return buyerParty;
    }

    public void setBuyerParty(String buyerParty) {
        this.buyerParty = buyerParty;
    }

    public String getSellerParty() {
        return sellerParty;
    }

    public void setSellerParty(String sellerParty) {
        this.sellerParty = sellerParty;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public String getPremiumCurrency() {
        return premiumCurrency;
    }

    public void setPremiumCurrency(String premiumCurrency) {
        this.premiumCurrency = premiumCurrency;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", buyerParty='" + buyerParty + '\'' +
                ", sellerParty='" + sellerParty + '\'' +
                ", premiumAmount=" + premiumAmount +
                ", premiumCurrency='" + premiumCurrency + '\'' +
                '}';
    }
}

