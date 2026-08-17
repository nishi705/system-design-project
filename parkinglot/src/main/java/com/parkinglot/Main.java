package com.parkinglot;

import com.parkinglot.factory.PaymentFactory;
import com.parkinglot.factory.VehicleFactory;
import com.parkinglot.model.*;
import com.parkinglot.model.enums.SpotSize;
import com.parkinglot.model.enums.VehicleType;
import com.parkinglot.strategy.parkingstrategy.NearByParkingStrategy;
import com.parkinglot.strategy.parkingstrategy.ParkingStrategy;
import com.parkinglot.strategy.paymentstrategy.CashPayment;
import com.parkinglot.strategy.paymentstrategy.CreditCardPayment;
import com.parkinglot.strategy.paymentstrategy.PaymentStrategy;
import com.parkinglot.strategy.pricingstrategy.PricingStrategy;
import com.parkinglot.strategy.pricingstrategy.VariableRatePricingStrategy;


import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
   static Map<Integer, Ticket> ticketMap = new HashMap<>();
    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);

        //try to create the floor in main and pass to strategy
        List<ParkingFloor> floors = new ArrayList<>();
        Map<SpotSize, Integer> spotCount = Map.of(
                SpotSize.SMALL, 2,
                SpotSize.MEDIUM,2,
                SpotSize.LARGE, 1
        );

        for(int i=0;i<2;i++){
            ParkingFloor floor = new ParkingFloor(i);
            for(Map.Entry<SpotSize, Integer> entry: spotCount.entrySet()){

               SpotSize size = entry.getKey();
               int count = entry.getValue();

                for(int j=0;j<count;j++){
                    ParkingSpot spot = new ParkingSpot(j+1, size);
                    floor.addAvailableSpots(spot);
                }
            }
            floors.add(floor);
        }


        ParkingStrategy parkingStrategy = new NearByParkingStrategy(floors);
        PricingStrategy pricingStrategy = new VariableRatePricingStrategy();
        ParkingLot parkingLot = new ParkingLot(floors, parkingStrategy,pricingStrategy);

        while (true) {
            System.out.println("enter the number to park and unpark the vehicle");

            int number;

            try {
                 number = scn.nextInt();
            }catch (InputMismatchException e){
                System.out.println("please enter only 1 or 2");
                scn.next();
                continue;
            }

            if (number == 1) {

                System.out.println("Enter your vehicle type: ");
                String vehicleType = scn.next();

                Vehicle vehicle = VehicleFactory.createVehicle(vehicleType);

                Ticket ticket = parkingLot.parkVehicle(vehicle);

                //parking strategy have three different messages
                if (!"parked successfully".equals(ticket.getMessage())) {
                    System.out.println(ticket.getMessage());
                    continue;
                }
                ticketMap.put(ticket.getTicketId(),ticket);
                System.out.println(ticket.getMessage() + "with ticketId: " + ticket.getTicketId());
            }else if (number == 2){
                System.out.println("Enter your ticketId");
                int ticketId = scn.nextInt();

                if(ticketMap.get(ticketId) == null){
                    System.out.println("Invalid TicketId");
                    continue;
                }
               double totalCharge = parkingLot.calculateCharge(ticketMap.get(ticketId));
               // double totalCharge = pricingStrategy.calculatePricing(ticketMap.get(ticketId));

                System.out.println("Enter your  paymentMode");
                String paymentMode = scn.next();
                PaymentStrategy paymentStrategy = PaymentFactory.createPaymentStrategy(paymentMode);
               if(paymentStrategy == null){
                   System.out.println("please enter the valid payment mode");
                   continue;
               }
               boolean paymentSuccessful = paymentStrategy.pay(totalCharge);

                if(paymentSuccessful){
                    parkingLot.unparkVehicle(ticketMap.get(ticketId));
                    ticketMap.remove(ticketId);
                    System.out.println("vehicle unparked with charge: "+ totalCharge);
                }

            }
        }
    }
}