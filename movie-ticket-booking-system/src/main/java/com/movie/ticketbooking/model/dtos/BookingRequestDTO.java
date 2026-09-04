package com.movie.ticketbooking.model.dtos;


import com.movie.ticketbooking.model.emuns.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {

    private Long userId;
    private Long showId;
    private List<Long> seatIds;

}
