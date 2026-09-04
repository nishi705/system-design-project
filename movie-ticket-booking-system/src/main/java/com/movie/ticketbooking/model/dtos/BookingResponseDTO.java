package com.movie.ticketbooking.model.dtos;

import com.movie.ticketbooking.model.emuns.BookingStatus;
import com.movie.ticketbooking.model.emuns.ScreenType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BookingResponseDTO {

    private Long bookingId;            // Matches Booking entity's primary key
    private Long userId;
    private Long showId;
    private List<Long> seatIds;
    private Double totalAmount;
    private BookingStatus bookingStatus; // CONFIRMED, CANCELLED, etc.
    private String formattedShowDateTime; // "Tue, 25 Aug | 10:00 AM"
    private String cancellationMessage;

}
