package com.parkinglot;

import com.parkinglot.factory.VehicleFactory;
import com.parkinglot.model.ParkingFloor;
import com.parkinglot.model.ParkingLot;
import com.parkinglot.model.Ticket;
import com.parkinglot.model.Vehicle;
import com.parkinglot.model.enums.VehicleType;
import com.parkinglot.strategy.parkingstrategy.NearByParkingStrategy;
import com.parkinglot.strategy.parkingstrategy.ParkingStrategy;
import com.parkinglot.strategy.paymentstrategy.CashPayment;
import com.parkinglot.strategy.paymentstrategy.CreditCardPayment;
import com.parkinglot.strategy.paymentstrategy.PaymentStrategy;
import com.parkinglot.strategy.pricingstrategy.PricingStrategy;
import com.parkinglot.strategy.pricingstrategy.VariableRatePricingStrategy;


import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
   static Map<Integer, Ticket> ticketMap = new HashMap<>();
    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        ParkingLot parkingLot = new ParkingLot();

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
                ticketMap.put(ticket.getTicketId(),ticket);
                if (ticket.getMessage().equals("empty spot not available")) {
                    System.out.println(ticket.getMessage());
                    break;
                }
                System.out.println(ticket.getMessage() + "with ticketId: " + ticket.getTicketId());
            }else if (number == 2){
                System.out.println("Enter your ticketId");
                int ticketId = scn.nextInt();

                PricingStrategy pricingStrategy = new VariableRatePricingStrategy();
                double totalCharge = pricingStrategy.calculatePricing(ticketMap.get(ticketId));

                System.out.println("Enter your  paymentMode");
                String paymentMode = scn.next();
                PaymentStrategy paymentStrategy = switch (paymentMode){
                    case "CASH" -> new CashPayment();
                    case "CREDITCARD" -> new CreditCardPayment();
                    default -> null;
                };

                boolean paymentSuccessful = paymentStrategy.pay(totalCharge);

                if(paymentSuccessful){
                    parkingLot.unparkVehicle(ticketMap.get(ticketId));
                    System.out.println("vehicle unparked with charge: "+ totalCharge);
                }

            }
        }
    }
}