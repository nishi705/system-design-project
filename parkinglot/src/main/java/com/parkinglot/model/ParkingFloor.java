package com.parkinglot.model;

import com.parkinglot.model.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class ParkingFloor {

    List<ParkingSpot> parkingSpotList;
    private int floorNumber;

    public ParkingFloor(int floorNumber) {
        parkingSpotList = new ArrayList<>();
        this.floorNumber = floorNumber;
    }
    public int getFloorNumber(){
        return  this.floorNumber;
    }


    public void addParkingSpot(ParkingSpot spot) {
        parkingSpotList.add(spot);
    }
    public ParkingSpot parkVehicle(VehicleType vehicleType){
        for(ParkingSpot spot: parkingSpotList) {
            if(spot.canFitVehicle(vehicleType) && spot.parkVehicle(vehicleType)) {
                //here in below line also returning the true/false still we are not
                //doing if(spot.parkVehicle(vehicleType); and not handeling false condition
                //because already spot.isEmpty() checked that the spot is empty, parkVehicle() should succeed.
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

}
