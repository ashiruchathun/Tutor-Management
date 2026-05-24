package com.tutorportal.adminportal.service;

import com.tutorportal.adminportal.model.Tutor;
import com.tutorportal.adminportal.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public Tutor addTutor(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    public Optional<Tutor> getTutorById(Long id) {
        return tutorRepository.findById(id);
    }

    public Tutor updateTutor(Long id, Tutor updatedTutor) {
        return tutorRepository.findById(id).map(tutor -> {
            tutor.setFullName(updatedTutor.getFullName());
            tutor.setEmail(updatedTutor.getEmail());
            tutor.setPhone(updatedTutor.getPhone());
            tutor.setSubject(updatedTutor.getSubject());
            tutor.setHourlyRate(updatedTutor.getHourlyRate());
            tutor.setStatus(updatedTutor.getStatus());
            return tutorRepository.save(tutor);
        }).orElseThrow(() -> new RuntimeException("Tutor not found with id: " + id));
    }

    public void deleteTutor(Long id) {
        tutorRepository.deleteById(id);
    }

    public long countActiveTutors() {
        return tutorRepository.countActiveTutors();
    }

    public List<Tutor> searchBySubject(String subject) {
        return tutorRepository.findBySubjectContainingIgnoreCase(subject);
    }
}
