package com.example.Home.Tutor.Search.and.Booking.System.Service;

import com.example.Home.Tutor.Search.and.Booking.System.model.Tutor;
import java.util.List;

public interface TutorService {

    Tutor addTutor(Tutor tutor);

    List<Tutor> getAllTutors();

    Tutor getTutorById(Long id);

    List<Tutor> searchBySubject(String subject);

    List<Tutor> searchByName(String name);

    List<Tutor> searchByTutorType(String tutorType);

    Tutor updateTutor(Long id, Tutor tutor);

    void deleteTutor(Long id);
}