package com.movie.ticketbooking.model.dtos;

import com.movie.ticketbooking.model.emuns.ScreenType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class TheatreShowtimeResponseDTO {

    private Long showId;
    private String theatreName;
    private LocalTime startTime;
    private Integer auditoriumNumber;
    private ScreenType screenType;
    private Double ticketPrice;


    public TheatreShowtimeResponseDTO(Long showId, String theatreName, LocalTime startTime, Integer auditoriumNumber, ScreenType screenType, Double ticketPrice) {
        this.showId = showId;
        this.theatreName = theatreName;
        this.startTime = startTime;
        this.auditoriumNumber = auditoriumNumber;
        this.screenType = screenType;
        this.ticketPrice = ticketPrice;
    }
}
