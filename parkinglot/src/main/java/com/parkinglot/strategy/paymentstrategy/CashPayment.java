package com.parkinglot.strategy.paymentstrategy;

public class CashPayment implements PaymentStrategy{
    @Override
    public boolean pay(double amount) {
        System.out.println("cash payment of rupee "+ amount + "paid successfully");
        return true;
    }
}
