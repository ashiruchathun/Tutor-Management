package com.example.Home.Tutor.Search.and.Booking.System.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tutors")
public class Tutor extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutorId;

    private String subject;
    private String qualification;
    private int experienceYears;
    private double hourlyRate;
    private String availability;
    private String tutorType; // Online, Home Visit, Hybrid

    // Required by JPA and JSON deserialization.
    public Tutor() {
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public String getSubject() {
        return subject;
    }

    public String getQualification() {
        return qualification;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public String getAvailability() {
        return availability;
    }

    public String getTutorType() {
        return tutorType;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setTutorType(String tutorType) {
        this.tutorType = tutorType;
    }
}
