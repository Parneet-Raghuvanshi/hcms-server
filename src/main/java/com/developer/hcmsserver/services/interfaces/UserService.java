package com.developer.hcmsserver.services.interfaces;

import com.developer.hcmsserver.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

/**
 * All userService function for Database Interaction
 * Contains :-
 * - /signup
 * - /login
 * */

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUser(String userId);
}
