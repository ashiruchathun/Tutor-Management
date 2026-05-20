package com.example.Home.Tutor.Search.and.Booking.System.controller;

import com.example.Home.Tutor.Search.and.Booking.System.Service.TutorService;
import com.example.Home.Tutor.Search.and.Booking.System.model.Tutor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutors")
@CrossOrigin(origins = "*")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    // Admin-only write API; public users can only read tutor data.
    @PostMapping
    public Tutor addTutor(@RequestBody Tutor tutor) {
        return tutorService.addTutor(tutor);
    }

    @GetMapping
    public List<Tutor> getAllTutors() {
        return tutorService.getAllTutors();
    }

    @GetMapping("/{id}")
    public Tutor getTutorById(@PathVariable Long id) {
        return tutorService.getTutorById(id);
    }

    @GetMapping("/search/subject")
    public List<Tutor> searchBySubject(@RequestParam String subject) {
        return tutorService.searchBySubject(subject);
    }

    @GetMapping("/search/name")
    public List<Tutor> searchByName(@RequestParam String name) {
        return tutorService.searchByName(name);
    }

    @GetMapping("/search/type")
    public List<Tutor> searchByTutorType(@RequestParam String tutorType) {
        return tutorService.searchByTutorType(tutorType);
    }

    @PutMapping("/{id}")
    public Tutor updateTutor(@PathVariable Long id, @RequestBody Tutor tutor) {
        return tutorService.updateTutor(id, tutor);
    }

    @DeleteMapping("/{id}")
    public String deleteTutor(@PathVariable Long id) {
        tutorService.deleteTutor(id);
        return "Tutor deleted successfully";
    }
}
