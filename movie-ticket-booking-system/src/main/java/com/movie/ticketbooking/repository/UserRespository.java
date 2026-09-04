package com.movie.ticketbooking.repository;

import com.movie.ticketbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {
}
