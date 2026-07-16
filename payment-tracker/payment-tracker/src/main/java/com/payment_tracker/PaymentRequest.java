package com.payment_tracker;


public class PaymentRequest {
    private String senderId;
    private String receiverId;
    private double amount;

    public String getSenderId(){
        return this.senderId = senderId;
    }

    public  String getReceiverId(){
        return this.receiverId = receiverId;
    }
}
