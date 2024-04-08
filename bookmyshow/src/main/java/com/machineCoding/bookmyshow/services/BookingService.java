package com.machineCoding.bookmyshow.services;

import com.machineCoding.bookmyshow.models.*;
import com.machineCoding.bookmyshow.repositories.BookingRepository;
import com.machineCoding.bookmyshow.repositories.ShowRepository;
import com.machineCoding.bookmyshow.repositories.ShowSeatRepository;
import com.machineCoding.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PricingService pricingService;
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(UserRepository userRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository,
                          PricingService pricingService,
                          BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.pricingService = pricingService;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE) //Concurrency is being handled at DB level
    public Booking bookMovie(String userId, List<Long> seatIds, Long showId)
    {   //------Start Lock here for "Now", but the below should be the actual steps
        /* 1. Get the user from userId
        *  2. Get the show from the showId
        *  ---------Take a lock(Actual)------------
        *  3. Get the showSeats from seatIds
        *  4. Check if the seats are available
        *  5. If yes, make the status as blocked or "booking in progress"
        *  ---------Release the lock(Actual)--------
        *  6. Save updated show seats in DB and end the lock
        *  ---------End the Lock here for "Now"--------
        * *///All of this is to make the particular seats are reserved and the 15 min timer
        //has started and user will see a confirmation page after which they need to make the payment
            //in 15 mins
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new RuntimeException();
        User bookedBy = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty())
            throw new RuntimeException();
        Show bookedShow = showOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatIds);
        for(ShowSeat showSeat : showSeats)
        {
            if(!((showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE))||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED)&&(
                            Duration.between(showSeat.getBlockedAt().toInstant(),new Date().toInstant()).toMinutes()>15
                            ))))
                throw new RuntimeException();
        }

        Date seatBlockedAt = new Date();
        List<ShowSeat>savedShowSeat = new ArrayList<>();// I need to save it in the booking obj as well
        for(ShowSeat showSeat : showSeats)
        {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setBlockedAt(seatBlockedAt);
            savedShowSeat.add(showSeatRepository.save(showSeat));
        }

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setBookedAt(new Date());
        booking.setShow(bookedShow);
        booking.setUser(bookedBy);
        booking.setShowSeats(savedShowSeat);
        booking.setAmount(pricingService.calculatePrice(savedShowSeat,bookedShow));

        Booking savedBooking = bookingRepository.save(booking);

        return savedBooking;
    }
}
