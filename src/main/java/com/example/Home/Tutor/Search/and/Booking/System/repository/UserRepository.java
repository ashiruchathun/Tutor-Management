package com.example.Home.Tutor.Search.and.Booking.System.repository;

import com.example.Home.Tutor.Search.and.Booking.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
