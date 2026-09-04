package com.movie.ticketbooking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Setter
@Getter
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private double ticketPrice;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    @OneToMany(mappedBy = "show")
    private List<Booking> booking;

    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;
}
