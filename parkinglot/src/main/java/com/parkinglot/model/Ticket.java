package com.parkinglot.model;

import com.parkinglot.model.enums.VehicleType;

import java.time.Instant;
import java.util.Date;

public class Ticket {
    private int ticketId;
    private Vehicle vehicle;
   private Instant startTime;
   private String message;
   private int spotNumber;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public static class TicketBuilder{
        private int ticketId;
        private Vehicle vehicle;
        private Instant startTime;
        private String message;
        private int spotNumber;

        public TicketBuilder ticketId(int ticketId){
            this.ticketId = ticketId;
            return this;
        }

        public TicketBuilder vehicle(Vehicle vehicle){
            this.vehicle = vehicle;
            return this;
        }
        public TicketBuilder startTime(Instant startTime){
            this.startTime = startTime;
            return this;
        }
        public TicketBuilder message(String message){
            this.message = message;
            return this;
        }
        public TicketBuilder spotNumber(int spotNumber){
            this.spotNumber = spotNumber;
            return this;
        }

        public Ticket build(){
            Ticket ticket = new Ticket();
            ticket.ticketId = this.ticketId;
            ticket.vehicle = this.vehicle;
            ticket.startTime = this.startTime;
            ticket.message = this.message;
            ticket.spotNumber = this.spotNumber;

            return ticket;
        }

    }
}
