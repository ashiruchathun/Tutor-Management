package com.example.Home.Tutor.Search.and.Booking.System.repository;

import com.example.Home.Tutor.Search.and.Booking.System.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    List<Tutor> findBySubjectContainingIgnoreCase(String subject);

    List<Tutor> findByNameContainingIgnoreCase(String name);

    List<Tutor> findByTutorTypeContainingIgnoreCase(String tutorType);
}