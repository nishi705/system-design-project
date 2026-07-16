package com.payment_tracker.customeexception;

public class PaymentNotFoundException extends RuntimeException{

    public PaymentNotFoundException(String msg){
        super(msg);
    }
}
