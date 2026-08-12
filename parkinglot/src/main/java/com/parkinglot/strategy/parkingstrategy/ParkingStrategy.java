package com.parkinglot.strategy.parkingstrategy;

import com.parkinglot.model.Ticket;
import com.parkinglot.model.Vehicle;

public interface ParkingStrategy {

public Ticket parkVehicle(Vehicle vehicle);

}
