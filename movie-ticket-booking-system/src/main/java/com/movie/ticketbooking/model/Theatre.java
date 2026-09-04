package com.movie.ticketbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "theatre", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"city_id", "theatre_name"})
})
@Setter
@Getter
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theatreId;
    private String theatreName;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private List<Auditorium> auditoriumList;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}
