package com.developer.hcmsserver.controller;

import com.developer.hcmsserver.dto.UserDto;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.models.GeneralResponse;
import com.developer.hcmsserver.models.requests.UserSignupRequest;
import com.developer.hcmsserver.models.responses.UserDataResponse;
import com.developer.hcmsserver.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * User endpoint controller
 * Contains :-
 * - /signup
 * - /login
 * */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    final ModelMapper mapper = new ModelMapper();

    @PostMapping("/signup")
    public GeneralResponse createUser(@RequestBody UserSignupRequest userSignupRequest) {
        if (userSignupRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        UserDto userDto = mapper.map(userSignupRequest,UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        UserDataResponse userDataResponse = mapper.map(createdUser, UserDataResponse.class);
        return new GeneralResponse("User Created Successfully!",userDataResponse);
    }
}
