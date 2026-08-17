package com.parkinglot.model;


import com.parkinglot.model.enums.SpotSize;
import com.parkinglot.model.enums.VehicleType;

public class ParkingSpot {
    private VehicleType vehicleType;
    private int sptNumber;
    private SpotSize spotSize;


    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public SpotSize getSpotSize() {
        return spotSize;
    }

    public void setSpotSize(SpotSize spotSize) {

        this.spotSize = spotSize;
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

    public ParkingSpot(int sptNumber,SpotSize spotSize){
        this.sptNumber = sptNumber;
         this.spotSize = spotSize;
    }

    //parkVehicle() should change the state.
    public boolean parkVehicle(VehicleType vehicleType){
        if (!isEmpty()) {
            return false;
        }

        this.vehicleType = vehicleType;
        //this line is doing actually is: this line is parking the vehicle
        //at vehicle when there were no any vehicle send to park at that time this.vehicleType = null
        //but as soon as any vehicle send to park then it will be this.vehicleType = vehicleType(CAR);
        return true;
    }

    //isEmpty() should only check the state of the vehicle
    public boolean isEmpty() {
        return vehicleType == null;
        //1.in very initial when spot is created at that time there were no vehicle int the
        //spot therefor vehicleType == null is true;
        //second time when any vehicle parked at that time vehicleType==null false and it
        //return false;
    }

    public void unparkVehicle() {

        this.vehicleType = null;
    }
    public boolean canFitVehicle(VehicleType type){

        return switch (type){
            case BIKE ->
                spotSize == SpotSize.SMALL || spotSize == SpotSize.MEDIUM || spotSize ==SpotSize.LARGE;
            case CAR ->
                spotSize == SpotSize.MEDIUM || spotSize == SpotSize.LARGE;
            case TRUCK ->
                spotSize == SpotSize.LARGE;
            default -> false;
        };

    }
}
