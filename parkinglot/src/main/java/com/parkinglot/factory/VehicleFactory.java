package com.parkinglot.factory;

import com.parkinglot.model.Vehicle;
import com.parkinglot.model.enums.VehicleType;

import java.util.concurrent.ThreadLocalRandom;

public class VehicleFactory {

    public static Vehicle createVehicle(String vehicleType){
        VehicleType vehType = switch (vehicleType){
            case "CAR" -> VehicleType.CAR;
            case "BIKE" -> VehicleType.BIKE;
            case "TRUCK" -> VehicleType.TRUCK;
            default -> VehicleType.UNKNOWN;
        };

       int number = ThreadLocalRandom.current().nextInt(1000,10000);
       String vehNumber = "Veh" + number;
       return new Vehicle(vehNumber, vehType);
    }
}
