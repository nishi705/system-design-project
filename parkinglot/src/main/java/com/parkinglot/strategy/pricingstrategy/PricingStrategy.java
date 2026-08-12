package com.parkinglot.strategy.pricingstrategy;


import com.parkinglot.model.Ticket;

public interface PricingStrategy {
    public double calculatePricing(Ticket ticket);
}
