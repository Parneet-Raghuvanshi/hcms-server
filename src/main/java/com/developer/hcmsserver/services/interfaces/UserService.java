package com.developer.hcmsserver.services.interfaces;

import com.developer.hcmsserver.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

public interface UserService extends UserDetailsService {
    void createUser(UserDto userDto);
    UserDto getUser(String publicId);
    boolean requestPasswordReset(String email);
    boolean resetPassword(String token,String password);
    void updateUserName(String id, String name);
    boolean verifyEmailToken(String token);
}
