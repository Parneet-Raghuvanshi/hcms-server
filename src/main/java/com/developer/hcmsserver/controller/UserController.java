package com.developer.hcmsserver.controller;

import com.developer.hcmsserver.dto.UserDto;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.models.GeneralResponse;
import com.developer.hcmsserver.models.requests.PasswordResetRequest;
import com.developer.hcmsserver.models.requests.UserNameUpdateRequest;
import com.developer.hcmsserver.models.requests.UserSignupRequest;
import com.developer.hcmsserver.services.interfaces.UserService;
import com.developer.hcmsserver.utils.GeneralUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;

    @PostMapping("/check")
    public GeneralResponse checkTokenExpired(@RequestBody Map<String, Object> payload) {
        boolean result = GeneralUtils.hasTokenExpired(payload.get("token").toString());
        return new GeneralResponse(result,"GENERAL","Token Check",result);
    }

    @PostMapping("/signup")
    public GeneralResponse createUser(@RequestBody UserSignupRequest userSignupRequest) {
        if (userSignupRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        UserDto userDto = mapper.map(userSignupRequest,UserDto.class);
        userService.createUser(userDto);
        return new GeneralResponse(false,"SUCCESS","User Created Successfully!",null);
    }

    @PostMapping("/password-reset-request")
    public GeneralResponse requestPasswordReset(@RequestBody PasswordResetRequest passwordResetRequest) {
        if (passwordResetRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        boolean operationResult = userService.requestPasswordReset(passwordResetRequest.getEmail());
        if (!operationResult) throw new ServerException(UserServiceException.UNKNOWN_EXCEPTION,HttpStatus.INTERNAL_SERVER_ERROR);
        return new GeneralResponse(false,"SUCCESS","Password reset mail sent successfully!",null);
    }

    @PostMapping("/updateName")
    public GeneralResponse updateUserName(@RequestBody UserNameUpdateRequest updateRequest) {
        if (updateRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        userService.updateUserName(updateRequest.getEmail(),updateRequest.getName());
        return new GeneralResponse(false,"SUCCESS","User Name Updated Successfully!",null);
    }
}
