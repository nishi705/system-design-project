package com.movie.ticketbooking.service;

import com.movie.ticketbooking.model.Booking;
import com.movie.ticketbooking.model.Seat;
import com.movie.ticketbooking.model.Show;
import com.movie.ticketbooking.model.User;
import com.movie.ticketbooking.model.dtos.BookingRequestDTO;
import com.movie.ticketbooking.model.dtos.BookingResponseDTO;
import com.movie.ticketbooking.model.dtos.ShowSeatResponseDTO;
import com.movie.ticketbooking.model.emuns.BookingStatus;
import com.movie.ticketbooking.repository.BookingRepository;
import com.movie.ticketbooking.repository.SeatRepository;
import com.movie.ticketbooking.repository.ShowRepository;
import com.movie.ticketbooking.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private BookingRepository bookingRepository;


    public List<ShowSeatResponseDTO> getSeatLayoutForShow(Long showId){

        Show show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));

        List<Seat> seatList = seatRepository.findSeatLayoutByShowId(showId);

       // Set<Long> bookedSeatIds = HashSet<>

        return seatList.stream().map(seat -> ShowSeatResponseDTO.builder()
                .seatId(seat.getSeatId())
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .price(show.getTicketPrice())
                .build()).toList();

    }

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {
        /*
        Use @Transactional to guarantee that fetching data, double-booking
        validation, and persistence execute safely inside a single database
        transaction:
         */


        //1.fetch and validate the user
        User user = userRespository.findById(requestDTO.getUserId()).orElseThrow(() -> new RuntimeException("user not find with this id"));

        //2. Fetch and validate Show
        Show show = showRepository.findById(requestDTO.getShowId()).orElseThrow(() -> new RuntimeException("show not found with this showId"));


        //3.fetch the requested seat
        List<Seat> seatList = seatRepository.findAllById(requestDTO.getSeatIds());
        if (seatList.size() != requestDTO.getSeatIds().size()) {
            throw new RuntimeException("one or more seat ids are provided");
        }

        //Already bookedSeat validation
        List<Long> bookedSeatIds = bookingRepository.findBookedSeatIdsForShow(requestDTO.getShowId(), BookingStatus.CONFIRMED);
        boolean isAlreadyBooked = seatList.stream()
                .anyMatch(seat -> bookedSeatIds.contains(seat.getSeatId()));

        if (isAlreadyBooked) {
            throw new RuntimeException("one more selected seats are already booked for id");

        }

        //calculate the total amount
        Double totalAmount = show.getTicketPrice() * seatList.size();

        LocalDateTime showDateTime = LocalDateTime.of(show.getDate(), show.getStartTime());
        //FormatedDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, d MMM | hh:mm a");
        String formatedDateShowDateTime = showDateTime.format(dateTimeFormatter);


        String cancellationMsg = "Cancellation available up to 20 minutes before show start time.";

        //Create Booking object
        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .seat(seatList)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();


        Booking savedBooking = bookingRepository.save(booking);

        return BookingResponseDTO.builder()
                .bookingId(savedBooking.getBookingId())
                .userId(user.getUserId())
                .showId(show.getShowId())
                .seatIds(seatList.stream().map(Seat::getSeatId).toList())
                .bookingStatus(savedBooking.getBookingStatus())
                .totalAmount(totalAmount)
                .formattedShowDateTime(formatedDateShowDateTime)
                .cancellationMessage(cancellationMsg)
                .build();


    }

    @Transactional
    public List<BookingResponseDTO> getUserBookingDetail(Long userId){
        if(!userRespository.existsById(userId)){
            throw new RuntimeException("user not found with id"+ userId);
        }

        List<Booking> bookings = bookingRepository.findByUser_UserId(userId);
        return bookings.stream().map(this::mapToBookingResponseDTO).toList();

    }

    @Transactional
    public BookingResponseDTO cancelBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("booking does not exist with this id:"+bookingId));

        if(booking.getBookingStatus() == BookingStatus.CANCELLED){
            throw new RuntimeException("booking already cancelled");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        Booking updatebooking = bookingRepository.save(booking);

        return mapToBookingResponseDTO(updatebooking);
    }

    private BookingResponseDTO mapToBookingResponseDTO(Booking booking){
        Show show = booking.getShow();
        LocalDateTime showDateTime = LocalDateTime.of(show.getDate(), show.getStartTime());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, d MMM | hh:mm a");

        String formatedDateTime = showDateTime.format(dateTimeFormatter);

        Double calculatedTotalAmount = show.getTicketPrice() * (booking.getSeat() != null ? booking.getSeat().size() : 0);

        List<Long> seatIds = booking.getSeat() != null
                             ? booking.getSeat().stream().map(Seat::getSeatId).toList()
                             :List.of();

        String cancellationMessage = booking.getBookingStatus() == BookingStatus.CANCELLED ?
                                      "Booking is already canceled" :
                                      "Cancellation available up to 20 minutes before show start time.";

     return BookingResponseDTO.builder()
             .bookingId(booking.getBookingId())
             .userId(booking.getUser().getUserId())
             .showId(show.getShowId())
             .seatIds(seatIds)
             .totalAmount(calculatedTotalAmount)
             .formattedShowDateTime(formatedDateTime)
             .cancellationMessage(cancellationMessage)
             .build();
    }

}
