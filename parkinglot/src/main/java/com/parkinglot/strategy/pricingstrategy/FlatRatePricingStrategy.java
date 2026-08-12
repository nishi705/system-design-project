package com.parkinglot.strategy.pricingstrategy;

import com.parkinglot.model.Ticket;

import java.time.Duration;
import java.time.Instant;

public class FlatRatePricingStrategy implements PricingStrategy{

    @Override
    public double calculatePricing(Ticket ticket){
        Duration duration = Duration.between(ticket.getStartTime(), Instant.now());

        long hour = Math.max(1, (long)Math.ceil(duration.toMinutes() / 60.0));

        return hour * 30;
    }
}
