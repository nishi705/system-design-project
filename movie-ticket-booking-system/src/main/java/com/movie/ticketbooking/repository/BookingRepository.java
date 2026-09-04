package com.movie.ticketbooking.repository;

import com.movie.ticketbooking.model.Booking;
import com.movie.ticketbooking.model.dtos.BookingResponseDTO;
import com.movie.ticketbooking.model.emuns.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

      @Query("SELECT s.seatId FROM Booking b JOIN b.seat s WHERE b.show.showId = :showId AND b.bookingStatus = :status")
      List<Long> findBookedSeatIdsForShow(@Param("showId") Long showId, @Param("status")BookingStatus status);

      List<Booking> findByUser_UserId(Long userId);
}
