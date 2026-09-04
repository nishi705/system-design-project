package com.movie.ticketbooking.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieListResponseDTO {
    //id, title, genre, language, poster URL
    private Long movieId;;
    private String movieName;
    private String language;

    public MovieListResponseDTO(Long movieId, String movieName, String language) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.language = language;
    }
}
