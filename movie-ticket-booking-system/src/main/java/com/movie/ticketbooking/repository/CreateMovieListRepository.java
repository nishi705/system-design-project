package com.movie.ticketbooking.repository;

import com.movie.ticketbooking.model.City;
import com.movie.ticketbooking.model.Movie;
import com.movie.ticketbooking.model.dtos.MovieListResponseDTO;
import com.movie.ticketbooking.model.dtos.TheatreShowtimeResponseDTO;
import com.movie.ticketbooking.model.emuns.ScreenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public interface CreateMovieListRepository extends JpaRepository<City, Long> {

    Optional<City> findByCityName(String cityName);

    @Query("SELECT DISTINCT new com.movie.ticketbooking.model.dtos.MovieListResponseDTO(m.movieId, m.movieName, m.language) FROM City c " +
            "JOIN c.theatreList t " +
            "JOIN t.auditoriumList a " +
            "JOIN a.showList s " +
            "JOIN s.movie m " +
            "WHERE LOWER(c.cityName) = LOWER(:cityName)")
    List<MovieListResponseDTO> findMovieByCityName(@Param("cityName") String cityName);

}
