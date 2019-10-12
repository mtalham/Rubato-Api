package no.rubato.service;

import no.rubato.controller.BookingInput;
import no.rubato.model.Booking;
import no.rubato.model.Persons;
import no.rubato.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookingService")
public class BookingService {
    private PersonsService personsService;
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(PersonsService personsService, BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        this.personsService = personsService;
    }

    public Booking createBooking(BookingInput booking, String username) {
        Persons user = personsService.getPersonByUsername(username);
        Persons band = personsService.findById(booking.getBandId());

        Booking newBooking = new Booking();
        newBooking.setUserId(user.getIdPerson());
        newBooking.setBand(band);
        newBooking.setFromDate(booking.getFromDate());
        newBooking.setToDate(booking.getToDate());
        return bookingRepository.save(newBooking);
    }

    public ResponseEntity<?> deleteBand(long bandId) {
        Booking existingBooking = bookingRepository.findById(bandId);
        if (existingBooking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bookingRepository.delete(existingBooking);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public List<Booking> getUserBookings(long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
