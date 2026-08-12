package com.parkinglot.strategy.pricingstrategy;

import com.parkinglot.model.Ticket;

import java.time.Duration;
import java.time.Instant;

public class VariableRatePricingStrategy implements PricingStrategy{
    @Override
    public double calculatePricing(Ticket ticket) {
        Duration duration = Duration.between(ticket.getStartTime(), Instant.now());
        long hours = Math.max(
                1,
                (long) Math.ceil(duration.toMinutes() / 60.0)
        );

        double totalCharge = switch (ticket.getVehicle().getVehicleType()) {
            case BIKE -> 20 * hours;
            case CAR -> 50 * hours;
            case TRUCK -> 100 * hours;
            default -> 0;
        };
        return totalCharge;
    }
}
