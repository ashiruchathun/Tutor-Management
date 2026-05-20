package com.example.Home.Tutor.Search.and.Booking.System.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {

    private String name;
    private String email;
    private String phone;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
