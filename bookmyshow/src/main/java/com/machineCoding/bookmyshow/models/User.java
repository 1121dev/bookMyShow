package com.machineCoding.bookmyshow.models;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    @OneToMany
    private List<Booking> bookings;
    private String password;
}
