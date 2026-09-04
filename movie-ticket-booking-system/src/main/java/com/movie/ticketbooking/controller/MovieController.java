package com.movie.ticketbooking.controller;

import com.movie.ticketbooking.model.City;
import com.movie.ticketbooking.model.dtos.*;
import com.movie.ticketbooking.repository.CreateMovieListRepository;
import com.movie.ticketbooking.repository.SeatRepository;
import com.movie.ticketbooking.repository.ShowRepository;
import com.movie.ticketbooking.service.BookingService;
import com.movie.ticketbooking.service.SaveCityMovieListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MovieController {

    @Autowired
    private SaveCityMovieListService saveCityMovieListService;
    @Autowired
    private CreateMovieListRepository createMovieListRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private BookingService bookingService;

    @PostMapping("/city")//catalog setup API
    public String cityMovieList(@RequestBody City city){
        saveCityMovieListService.saveCityMovieList(city);
        return "saved successfully";
    }

    @GetMapping("/cities/{cityName}/movies")
    public List<MovieListResponseDTO> getMovieList(@PathVariable String cityName){
        return createMovieListRepository.findMovieByCityName(cityName);
    }

    @GetMapping("/cities/{cityName}/movies/{movieId}/shows")
    public List<TheatreShowtimeResponseDTO> getShowTheatre(@PathVariable String cityName,
                                                           @PathVariable Long movieId,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return showRepository.findShowForMovies(cityName,movieId,date);

    }
    @GetMapping("/show/{showId}/seats")
    public ResponseEntity<List<ShowSeatResponseDTO>> getSeatLayoutDetail(@PathVariable Long showId){


          return ResponseEntity.ok(bookingService.getSeatLayoutForShow(showId));
    }

    @PostMapping("/booking")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request){
          BookingResponseDTO responseDTO = bookingService.createBooking(request);
          return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/bookingHistoryDetail")
    public ResponseEntity<List<BookingResponseDTO>> getBookingDetail(@PathVariable Long userId){
     List<BookingResponseDTO> bookings = bookingService.getUserBookingDetail(userId);
     return ResponseEntity.ok(bookings);
    }


    @PutMapping("/booking/{bookingId}/cancelBooking")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable Long bookingId){
        BookingResponseDTO response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

}