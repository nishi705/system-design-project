package com.parkinglot.model;

import com.parkinglot.model.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class ParkingFloor {

    List<ParkingSpot> parkingSpotList;

    public ParkingFloor() {
        parkingSpotList = new ArrayList<>();
    }

    public void addParkingSpot(ParkingSpot spot) {
        parkingSpotList.add(spot);
    }
    public ParkingSpot parkVehicle(VehicleType vehicleType){
        for(ParkingSpot spot: parkingSpotList) {
            if(spot.isEmpty()) {
                spot.parkVehicle(vehicleType);
                return spot;
            }
        }
        return null;
    }

    public ParkingSpot getParkingSpot(int spotNumber) {
        for(ParkingSpot spot : parkingSpotList){
            if (spot.getSptNumber() == spotNumber)
                return spot;
        }
        return null;
    }

    public void unparkVehicleFromSpot(int spotNumber) {
        for (ParkingSpot spot: parkingSpotList){
            if (spot != null) {
                spot.unparkVehicle();
            }
        }
    }
}
