package com.parkinglot.factory;

import com.parkinglot.strategy.paymentstrategy.CashPayment;
import com.parkinglot.strategy.paymentstrategy.CreditCardPayment;
import com.parkinglot.strategy.paymentstrategy.PaymentStrategy;

public class PaymentFactory {

    public static PaymentStrategy createPaymentStrategy(String paymentMode){

        PaymentStrategy paymentStrategy = switch (paymentMode){
            case "CASH" -> new CashPayment();
            case "CREDITCARD" -> new CreditCardPayment();
            default -> null;
        };
        return paymentStrategy;
    }
}
