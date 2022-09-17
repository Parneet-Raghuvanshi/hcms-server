package com.developer.hcmsserver.controller;

import com.developer.hcmsserver.dto.UserDto;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.models.GeneralResponse;
import com.developer.hcmsserver.models.requests.PasswordResetRequest;
import com.developer.hcmsserver.models.requests.UserSignupRequest;
import com.developer.hcmsserver.models.responses.UserDataResponse;
import com.developer.hcmsserver.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * User endpoint controller
 * Contains :-
 * - /signup
 * - /login
 * */

@RestController
@RequestMapping("/api/user")
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
        return new GeneralResponse(false,"SUCCESS","User Created Successfully!",userDataResponse);
    }

    @PostMapping("/password-reset-request")
    public GeneralResponse requestPasswordReset(@RequestBody PasswordResetRequest passwordResetRequest) {
        boolean operationResult = userService.requestPasswordReset(passwordResetRequest.getEmail());
        if (!operationResult) throw new ServerException(UserServiceException.UNKNOWN_EXCEPTION,HttpStatus.INTERNAL_SERVER_ERROR);
        return new GeneralResponse(false,"SUCCESS","Password reset mail sent successfully!",null);
    }
}
