package com.parkinglot.strategy.parkingstrategy;

import com.parkinglot.model.ParkingFloor;
import com.parkinglot.model.ParkingSpot;
import com.parkinglot.model.Ticket;
import com.parkinglot.model.Vehicle;
import com.parkinglot.model.enums.VehicleType;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class NearByParkingStrategy implements ParkingStrategy{

    private List<ParkingFloor> parkingFloorList;

    public NearByParkingStrategy(List<ParkingFloor> parkingFloorList){
        this.parkingFloorList = parkingFloorList;
    }
    @Override
    public Ticket parkVehicle(Vehicle vehicle) {


        if(vehicle.getVehicleType() == VehicleType.UNKNOWN){
            return new Ticket.TicketBuilder()
                    .vehicle(vehicle)
                    .message("please entered valid vehicle type")
                    .build();
        }

        for(ParkingFloor floor: parkingFloorList){
                ParkingSpot spot = floor.parkVehicle(vehicle.getVehicleType());

                if(spot != null) {
                   Ticket ticket = new Ticket.TicketBuilder()
                            .vehicle(vehicle)
                            .startTime(Instant.now())
                            .spotNumber(spot.getSptNumber())
                            .floorNumber(floor.getFloorNumber())
                            .message("parked successfully")
                            .build();
                   return ticket;
                }
        }


          return new Ticket.TicketBuilder()
                  .vehicle(vehicle)
                          .message("empty spot not available")
                                  .build();
    }

}
