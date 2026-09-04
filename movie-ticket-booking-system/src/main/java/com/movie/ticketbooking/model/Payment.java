package com.movie.ticketbooking.model;

import com.movie.ticketbooking.model.emuns.PaymentMode;
import com.movie.ticketbooking.model.emuns.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

}
