package com.developer.hcmsserver.security;

import com.developer.hcmsserver.dto.UserDto;
import com.developer.hcmsserver.models.requests.UserLoginRequest;
import com.developer.hcmsserver.services.interfaces.UserService;
import com.developer.hcmsserver.utils.SpringApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            UserLoginRequest cred = new ObjectMapper()
                    .readValue(req.getInputStream(), UserLoginRequest.class);

            UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImplementation");
            UserDto userDto = userService.getUser(cred.getEmail());

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(userDto.getRole()));

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            cred.getEmail(),
                            cred.getPassword(),
                            authorities)
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
