package com.machineCoding.bookmyshow.controllers;

import com.machineCoding.bookmyshow.dto.BookMovieRequestDto;
import com.machineCoding.bookmyshow.dto.BookMovieResponseDto;
import com.machineCoding.bookmyshow.dto.ResponseStatus;
import com.machineCoding.bookmyshow.models.Booking;
import com.machineCoding.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDto bookMovie(BookMovieRequestDto bookMovieRequestDto)
    {   BookMovieResponseDto bookMovieResponseDto = new BookMovieResponseDto();
        Booking booking;
        try{
            booking = bookingService.bookMovie(
                    String.valueOf(bookMovieRequestDto.getUserId()),
                    bookMovieRequestDto.getShowSeatIds(),
                    bookMovieRequestDto.getShowId());
            bookMovieResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            bookMovieResponseDto.setAmount(booking.getAmount());
            bookMovieResponseDto.setBookingId(booking.getId());
        }catch(Exception e)
        {
            bookMovieResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return bookMovieResponseDto;
    }
}
