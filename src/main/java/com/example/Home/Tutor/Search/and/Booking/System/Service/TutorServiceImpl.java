package com.example.Home.Tutor.Search.and.Booking.System.Service;

import com.example.Home.Tutor.Search.and.Booking.System.model.Tutor;
import com.example.Home.Tutor.Search.and.Booking.System.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;

    public TutorServiceImpl(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Override
    public Tutor addTutor(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    @Override
    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    @Override
    public Tutor getTutorById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Tutor not found with ID: " + id));
    }

    @Override
    public List<Tutor> searchBySubject(String subject) {
        return tutorRepository.findBySubjectContainingIgnoreCase(subject);
    }

    @Override
    public List<Tutor> searchByName(String name) {
        return tutorRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Tutor> searchByTutorType(String tutorType) {
        return tutorRepository.findByTutorTypeContainingIgnoreCase(tutorType);
    }

    @Override
    public Tutor updateTutor(Long id, Tutor tutor) {
        Tutor existingTutor = getTutorById(id);

        existingTutor.setName(tutor.getName());
        existingTutor.setEmail(tutor.getEmail());
        existingTutor.setPhone(tutor.getPhone());
        existingTutor.setSubject(tutor.getSubject());
        existingTutor.setQualification(tutor.getQualification());
        existingTutor.setExperienceYears(tutor.getExperienceYears());
        existingTutor.setHourlyRate(tutor.getHourlyRate());
        existingTutor.setAvailability(tutor.getAvailability());
        existingTutor.setTutorType(tutor.getTutorType());

        return tutorRepository.save(existingTutor);
    }

    @Override
    public void deleteTutor(Long id) {
        Tutor existingTutor = getTutorById(id);
        tutorRepository.delete(existingTutor);
    }
}