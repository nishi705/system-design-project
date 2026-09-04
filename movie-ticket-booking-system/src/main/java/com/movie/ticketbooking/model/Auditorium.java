package com.movie.ticketbooking.model;

import com.movie.ticketbooking.model.emuns.ScreenType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Auditorium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditoriumId;

    @Column(name = "auditorium_number")
    private Integer auditoriumNumber;

    //cascadetype.all ensures that any opertaion performed on the parent object
    //automaticallly tiggers the same exact same operation on the related child
    @OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL)
    private List<Seat> seatList;

    @OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL)
    private List<Show> showList;

    @Enumerated(EnumType.STRING)
    private ScreenType screenType;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

}
