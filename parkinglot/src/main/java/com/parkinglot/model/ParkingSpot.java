package com.parkinglot.model;


import com.parkinglot.model.enums.VehicleType;

public class ParkingSpot {
    private VehicleType vehicleType;
    private int sptNumber;


    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getSptNumber() {
        return sptNumber;
    }

    public void setSptNumber(int sptNumber) {
        this.sptNumber = sptNumber;
    }

    public ParkingSpot(){

    }

    //parkVehicle() should change the state.
    public boolean parkVehicle(VehicleType vehicleType){
        if (!isEmpty()) {
            return false;
        }

        this.vehicleType = vehicleType;

        return true;
    }

    //isEmpty() should only check the state of the vehicle
    public boolean isEmpty() {
        return vehicleType == null;
    }

    public void unparkVehicle() {

        this.vehicleType = null;
    }
}
