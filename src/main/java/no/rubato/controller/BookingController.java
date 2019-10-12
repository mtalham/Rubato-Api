package no.rubato.controller;

import no.rubato.model.Booking;
import no.rubato.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/booking")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) { this.bookingService = bookingService; }

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody BookingInput booking, Principal principal) {
        Booking newBooking = bookingService.createBooking(booking, principal.getName());
        return new ResponseEntity<>(newBooking, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") long bandId) {
        return bookingService.deleteBand(bandId);
    }

    @GetMapping("/user-bookings/{id}")
    public ResponseEntity<?> getUserBookings(@PathVariable("id") long userId) {
        return new ResponseEntity<>(bookingService.getUserBookings(userId), HttpStatus.OK);
    }
}
