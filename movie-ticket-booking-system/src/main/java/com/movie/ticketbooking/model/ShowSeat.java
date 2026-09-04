package com.movie.ticketbooking.model;

import com.movie.ticketbooking.model.emuns.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/*
If you stored status directly inside the permanent Seat entity, setting Seat A1
to BOOKED for the 10:00 AM show would incorrectly mark it as BOOKED for the 2:00 PM
show as well.

The ShowSeat class is required because a physical seat's availability changes
from show to show.
A physical seat (Seat) in an auditorium exists permanently (e.g., Seat "A1"
in Auditorium 1). However, whether Seat A1 is AVAILABLE, LOCKED, or BOOKED depends
entirely on which specific showtime you are looking at.
 */

@Entity
@Getter
@Setter
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showSeatId;

    @ManyToOne
    @JoinColumn(name = "show_id") // Explicitly names column show_id
    //1 Seat -> Many ShowSeat
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id") // Explicitly names column seat_id
    //(1 Show -> Many ShowSeat)
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus; // AVAILABLE, LOCKED, BOOKED
}
