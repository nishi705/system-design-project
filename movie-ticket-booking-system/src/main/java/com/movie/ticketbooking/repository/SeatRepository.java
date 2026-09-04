package com.movie.ticketbooking.repository;

import com.movie.ticketbooking.model.Seat;
import com.movie.ticketbooking.model.dtos.ShowSeatResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

//    @Query("SELECT new com.movie.ticketbooking.model.dtos.ShowSeatResponseDTO(" +
//            "s.seatId, " +
//            "s.seatNumber, " +
//            "s.seatType, " +
//            "sh.ticketPrice, " +
//            "CASE WHEN EXISTS (" +
//            "   SELECT 1 FROM Booking b JOIN b.seats bs " +
//            "   WHERE b.show.showId = :showId " +
//            "   AND bs.seatId = s.seatId " +
//            "   AND b.bookingStatus = com.movie.ticketbooking.model.emuns.BookingStatus.CONFIRMED" +
//            ") THEN com.movie.ticketbooking.model.emuns.SeatStatus.BOOKED " +
//            "ELSE com.movie.ticketbooking.model.emuns.SeatStatus.AVAILABLE END) " +
//            "FROM Seat s " +
//            "JOIN s.auditorium a " +
//            "JOIN a.showList sh " +
//            "WHERE sh.showId = :showId")
//  List<ShowSeatResponseDTO> findSeatLayoutByShowId(@Param("showId") Long showId);


    @Query("SELECT s FROM Seat s JOIN s.auditorium a JOIN a.showList sh WHERE sh.showId = :showId")
    List<Seat> findSeatLayoutByShowId(@Param("showId") Long showId);
}
