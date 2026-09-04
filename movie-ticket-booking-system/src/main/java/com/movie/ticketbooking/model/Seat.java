package com.movie.ticketbooking.model;

import com.movie.ticketbooking.model.emuns.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private String seatNumber; // e.g., "A1", "A2"

    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;
}
