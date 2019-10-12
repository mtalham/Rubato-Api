package no.rubato.repository;

import no.rubato.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findById(long id);
    List<Booking> findByUserId(long userId);
}
