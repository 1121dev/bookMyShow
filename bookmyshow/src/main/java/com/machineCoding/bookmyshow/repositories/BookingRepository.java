package com.machineCoding.bookmyshow.repositories;

import com.machineCoding.bookmyshow.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Override
    Optional<Booking> findById(Long aLong);//optional coz booking may be null also
    @Override
    Booking save(Booking booking);
}
