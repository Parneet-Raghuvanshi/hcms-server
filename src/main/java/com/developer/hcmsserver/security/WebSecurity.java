package com.developer.hcmsserver.security;

import com.developer.hcmsserver.dto.UserDto;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.models.GeneralResponse;
import com.developer.hcmsserver.services.interfaces.UserService;
import com.developer.hcmsserver.utils.SecurityConstants;
import com.developer.hcmsserver.utils.SpringApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGNUP_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL).permitAll()
                .antMatchers(HttpMethod.POST,SecurityConstants.PASSWORD_RESET_URL).permitAll()
                .antMatchers(HttpMethod.GET,SecurityConstants.PASSWORD_URL).permitAll()
                .antMatchers(HttpMethod.POST,SecurityConstants.PASSWORD_URL).permitAll()
                .anyRequest().authenticated()
                .and().addFilter(getAuthenticationFilter())
                .addFilter(new JwtFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
        filter.setAuthenticationSuccessHandler(this::authSuccessHandler);
        filter.setAuthenticationFailureHandler(this::authFailureHandler);
        return filter;
    }

    // Simple Configurations for CORS Policy
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    private void authSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String userName = ((User) authentication.getPrincipal()).getUsername();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String token = Jwts.builder()
                .setSubject(userName)
                .claim(SecurityConstants.AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.getTokenSecret())
                .compact();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImplementation");
        UserDto userDto = userService.getUser(userName);

        String tokenRes = SecurityConstants.TOKEN_PREFIX + token;

        // for sending proper response -- Custom Response
        Map<String, Object> details = new HashMap<>();
        details.put("token", tokenRes);
        details.put("userId", userDto.getUserId());
        GeneralResponse generalResponse = new GeneralResponse(false,"SUCCESS","Authentication successful!",details);

        // response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), generalResponse);
    }

    public void authFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        /**
         * Custom Exception will not work here as it is outside the controllers.
         * Filter Chain Component
         * */
        String name = exception.getClass().getSimpleName();
        switch (name) {
            case "DisabledException" -> {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                GeneralResponse generalResponse = new GeneralResponse(
                        true,UserServiceException.EMAIL_NOT_VERIFIED[0],
                        UserServiceException.EMAIL_NOT_VERIFIED[1],null);
                new ObjectMapper().writeValue(response.getWriter(), generalResponse);
            }
            case "UsernameNotFoundException" -> {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                GeneralResponse generalResponse = new GeneralResponse(
                        true,UserServiceException.USER_NOT_FOUND[0],
                        UserServiceException.USER_NOT_FOUND[1],null);
                new ObjectMapper().writeValue(response.getWriter(), generalResponse);
            }
            case "BadCredentialsException" -> {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                GeneralResponse generalResponse = new GeneralResponse(
                        true,UserServiceException.WRONG_PASSWORD[0],
                        UserServiceException.WRONG_PASSWORD[1],null);
                new ObjectMapper().writeValue(response.getWriter(), generalResponse);
            }
            default -> {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                GeneralResponse generalResponse = new GeneralResponse(
                        true,UserServiceException.UNKNOWN_EXCEPTION[0],
                        UserServiceException.UNKNOWN_EXCEPTION[1],null);
                new ObjectMapper().writeValue(response.getWriter(), generalResponse);
            }
        }
    }
}
