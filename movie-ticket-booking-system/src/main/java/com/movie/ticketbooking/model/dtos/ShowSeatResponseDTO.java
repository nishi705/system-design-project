package com.movie.ticketbooking.model.dtos;

import com.movie.ticketbooking.model.emuns.SeatStatus;
import com.movie.ticketbooking.model.emuns.SeatType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSeatResponseDTO {
    private Long seatId;
    private String seatNumber;
    private SeatType seatType;
    private Double price;
    private SeatStatus status; // AVAILABLE, BOOKED
}
