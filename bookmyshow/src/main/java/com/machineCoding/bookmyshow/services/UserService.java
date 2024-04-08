package com.machineCoding.bookmyshow.services;

import com.machineCoding.bookmyshow.models.User;
import com.machineCoding.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User signUp(String email, String password) {
        // Check if user is already there or not

        Optional<User> loggingUser = userRepository.findById(email);
        if(!loggingUser.isEmpty())
            return loggingUser.get();

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        User savedUser = userRepository.save(user);

        return savedUser;
    }
}
