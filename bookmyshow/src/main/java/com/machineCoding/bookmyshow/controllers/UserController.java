package com.machineCoding.bookmyshow.controllers;

import com.machineCoding.bookmyshow.dto.ResponseStatus;
import com.machineCoding.bookmyshow.dto.SignUpRequestDto;
import com.machineCoding.bookmyshow.dto.SignUpResponseDto;
import com.machineCoding.bookmyshow.models.User;
import com.machineCoding.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto request) {
        SignUpResponseDto response = new SignUpResponseDto();
        User user;

        try {
            user = userService.signUp(request.getEmail(), request.getPassword());

            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setUserId(user.getId());
        } catch(Exception ex) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }

        return response;
    }
}
