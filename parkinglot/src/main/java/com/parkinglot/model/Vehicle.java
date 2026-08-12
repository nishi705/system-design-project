package com.parkinglot.model;

import com.parkinglot.model.enums.VehicleType;

public class Vehicle {

    private String vehicleNumber;
    private VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType){
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber(){
        return this.vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber){
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleType getVehicleType(){
        return this.vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType){
        this.vehicleType = vehicleType;
    }
}
