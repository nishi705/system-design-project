package com.movie.ticketbooking.repository;

import com.movie.ticketbooking.model.Show;
import com.movie.ticketbooking.model.dtos.TheatreShowtimeResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("SELECT new com.movie.ticketbooking.model.dtos.TheatreShowtimeResponseDTO(" +
        "s.showId, t.theatreName, s.startTime, a.auditoriumNumber, a.screenType, s.ticketPrice )" +
            "FROM Show s " +
            "JOIN s.auditorium a " +
            "JOIN a.theatre t " +
            "JOIN t.city c " +
            "WHERE LOWER(c.cityName) = LOWER(:cityName) " +
            "AND s.movie.movieId = :movieId " +
            "AND s.date = :date")
     List<TheatreShowtimeResponseDTO> findShowForMovies(
           @Param("cityName") String cityName,
            @Param("movieId") Long movieId,
           @Param("date") LocalDate date);
}
