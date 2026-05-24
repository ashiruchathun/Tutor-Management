package com.tutorportal.adminportal.service;

import com.tutorportal.adminportal.model.Booking;
import com.tutorportal.adminportal.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking updateBookingStatus(Long id, String status) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus(status);
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public long countPendingBookings() {
        return bookingRepository.countPendingBookings();
    }

    public long countTotalBookings() {
        return bookingRepository.countTotalBookings();
    }
}
