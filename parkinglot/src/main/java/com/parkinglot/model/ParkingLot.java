package com.parkinglot.model;

import com.parkinglot.model.enums.VehicleType;
import com.parkinglot.strategy.parkingstrategy.NearByParkingStrategy;
import com.parkinglot.strategy.parkingstrategy.ParkingStrategy;


import java.time.Instant;
import java.util.ArrayList;

import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLot {
    private ParkingStrategy parkingStrategy;
    private AtomicInteger ticketCounter = new AtomicInteger(1);

    List<ParkingFloor> parkingFloorList = new ArrayList<>();


    public ParkingLot() {
        for (int i = 0; i < 2; i++) {
            ParkingFloor floor = new ParkingFloor();
            for (int j = 0; j < 5; j++) {
                floor.addParkingSpot(new ParkingSpot());
            }
            parkingFloorList.add(floor);
        }
        parkingStrategy = new NearByParkingStrategy(parkingFloorList);
    }

    public Ticket parkVehicle(Vehicle vehicle) {

        Ticket ticket = parkingStrategy.parkVehicle(vehicle);
        ticket.setTicketId(ticketCounter.getAndIncrement());
        return ticket;
    }

    public void unparkVehicle(Ticket ticket) {

        for (ParkingFloor floor : parkingFloorList) {
            ParkingSpot spot = floor.getParkingSpot(ticket.getSpotNumber());
            if (spot != null) {
                floor.unparkVehicleFromSpot(ticket.getSpotNumber());
                return;
            }
        }
    }
}

