package com.movie.ticketbooking.model;

import com.movie.ticketbooking.model.emuns.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Explicitly defines the booking_seat join table
    //here see one thing seat and booking is manytomany and below is the
    //correct way to create booking_seat table
    //if u dont use
    /*@OneToNay(mappedBy = "booking")
    private List<Seat> seat;*/
    //then automatically it creates booking_seat table
    //but below is the correct way to create booking_seat table
    @ManyToMany
    @JoinTable(
            name = "booking_seat",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seat;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

}
