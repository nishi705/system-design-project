package com.payment_tracker.customeexception;

public class PaymentConflictException extends RuntimeException{
    public PaymentConflictException(String msg){
        super(msg);
    }
}
