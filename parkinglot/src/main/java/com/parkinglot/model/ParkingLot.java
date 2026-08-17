package com.parkinglot.model;

import com.parkinglot.model.enums.SpotSize;
import com.parkinglot.model.enums.VehicleType;
import com.parkinglot.strategy.parkingstrategy.NearByParkingStrategy;
import com.parkinglot.strategy.parkingstrategy.ParkingStrategy;
import com.parkinglot.strategy.pricingstrategy.PricingStrategy;


import java.time.Instant;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

public class ParkingLot {
    private final ParkingStrategy parkingStrategy;
    private final AtomicInteger ticketCounter = new AtomicInteger(1);
    private final PricingStrategy pricingStrategy;

    List<ParkingFloor> parkingFloorList = new ArrayList<>();

//Map<SpotSize, Queue<ParkingSpot>>
    /*
    But be careful: spot IDs should probably be unique within a floor, or you should use
    a combined floor + spot identifier if your ticket needs to uniquely identify a spot across
     the entire parking lot.
     */
    public ParkingLot(List<ParkingFloor> parkingFloorList, ParkingStrategy parkingStrategy, PricingStrategy pricingStrategy) {
        //now parking lot does not care which strategy i am using.
        this.parkingStrategy = parkingStrategy;
        this.parkingFloorList = parkingFloorList;
        this.pricingStrategy = pricingStrategy;
    }

    public Ticket parkVehicle(Vehicle vehicle) {

        Ticket ticket = parkingStrategy.parkVehicle(vehicle);

        if("parked successfully".equals(ticket.getMessage())) {
            ticket.setTicketId(ticketCounter.getAndIncrement());
        }
        return ticket;
    }

    public double calculateCharge(Ticket ticket){
        return  pricingStrategy.calculatePricing(ticket);
    }

    public void unparkVehicle(Ticket ticket) {

        for (ParkingFloor floor : parkingFloorList) {
            ParkingSpot spot = floor.getParkingSpot(ticket.getSpotNumber());
            if (spot != null) {
                spot.unparkVehicle();
                return;
            }
        }
    }
}

