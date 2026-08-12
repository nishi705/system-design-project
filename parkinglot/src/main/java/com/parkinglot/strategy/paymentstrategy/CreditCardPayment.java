package com.parkinglot.strategy.paymentstrategy;

public class CreditCardPayment implements PaymentStrategy{
    @Override
    public boolean pay(double amount) {
        System.out.println("credit card payment of rupee " +amount + "paid successfully");
        return true;
    }
}
