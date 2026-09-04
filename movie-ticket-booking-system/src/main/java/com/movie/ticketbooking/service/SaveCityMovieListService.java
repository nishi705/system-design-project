package com.movie.ticketbooking.service;

import com.movie.ticketbooking.model.*;
import com.movie.ticketbooking.repository.CreateMovieListRepository;
import com.movie.ticketbooking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import java.util.HashMap;

@Service
public class SaveCityMovieListService {

    @Autowired
    private CreateMovieListRepository createMovieListRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public void saveCityMovieList(City cityPayload) {

        Map<String, Movie> movieCache = new HashMap<>();

        // 1. Fetch existing City or instantiate a clean new City (DO NOT save cityPayload yet)
        City city = createMovieListRepository.findByCityName(cityPayload.getCityName())
                .orElseGet(() -> {
                    City newCity = new City();
                    newCity.setCityName(cityPayload.getCityName());
                    return newCity;
                });

        if(cityPayload.getTheatreList() != null){
            for(Theatre theatre: cityPayload.getTheatreList()){
                theatre.setCity(city);

                if(theatre.getAuditoriumList() != null){
                    for(Auditorium auditorium: theatre.getAuditoriumList()){
                        auditorium.setTheatre(theatre);

                        if(auditorium.getSeatList() != null){
                            auditorium.getSeatList().forEach(seat -> seat.setAuditorium(auditorium));
                        }

                        if(auditorium.getShowList() != null){
                            for(Show show: auditorium.getShowList()){
                                show.setAuditorium(auditorium);

                                // 2. Fix NULL movie_id: Find existing movie or save new one

                                if(show.getMovie() != null){
                                    Movie moviePayload = show.getMovie();
                                    show.setMovie(null);
                                    Movie resolveMovie;

                                    if(moviePayload.getMovieId() != null){
                                        resolveMovie = movieRepository.findById(moviePayload.getMovieId()).orElseThrow(() -> new RuntimeException("MovieId not found" + moviePayload.getMovieId()));
                                    }else{
                                        // Cache lookup across all auditoriums and theatres
                                        resolveMovie = movieCache.computeIfAbsent(moviePayload.getMovieName(), name ->
                                                movieRepository.findByMovieName(name)
                                                        .orElseGet(() -> movieRepository.saveAndFlush(moviePayload))
                                        );
                                    }
                                    show.setMovie(resolveMovie);
                                }

                            }
                        }
                    }
                }
            }
            city.setTheatreList(cityPayload.getTheatreList());
        }
        createMovieListRepository.save(city);
    }
}
