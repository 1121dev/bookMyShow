package com.machineCoding.bookmyshow.models;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
@Entity
public class Booking extends BaseModel{
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    @ManyToMany
    private List<ShowSeat> showSeats;
    @ManyToOne
    private User user;
    private Date bookedAt;
    @ManyToOne
    private Show show;
    private int amount;
    @OneToMany
    private List<Payment> payments;
}
