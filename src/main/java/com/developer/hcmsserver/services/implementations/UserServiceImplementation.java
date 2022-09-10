package com.developer.hcmsserver.services.implementations;

import com.developer.hcmsserver.dto.UserDto;
import com.developer.hcmsserver.entity.UserEntity;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.repository.UserRepository;
import com.developer.hcmsserver.services.interfaces.UserService;
import com.developer.hcmsserver.utils.GeneralUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImplementation implements UserService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GeneralUtils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        // Check if user already exists or not...
        if (userRepository.findUserByEmail(userDto.getEmail()) != null)
            throw new ServerException(UserServiceException.RECORD_ALREADY_EXIST,HttpStatus.INTERNAL_SERVER_ERROR);

        UserEntity userEntity = mapper.map(userDto,UserEntity.class);
        // Compute Password and other things
        String publicUserId = userEntity.getRole() + "_" + utils.generateUserId(20);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setUserId(publicUserId);
        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(publicUserId));
        userEntity.setEmailVerificationStatus(Boolean.FALSE);

        // Save the Entity to the MySql Database using repository
        UserEntity savedUserEntity = userRepository.save(userEntity);
        // send email for verification...
        try {
             sendEmail(savedUserEntity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new ServerException(UserServiceException.MAIL_NOT_SENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // and send the UserDto back to Controller
        return mapper.map(savedUserEntity,UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findUserByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Here Username --> email (both are same)
        UserEntity userEntity = userRepository.findUserByEmail(email);

        // If the user not found for this email
        if (userEntity == null) throw new UsernameNotFoundException(email);

        // Fetching the USER_ROLE for authorization
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+userEntity.getRole()));

        // Returning the specific user fetched
        return new User(userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                userEntity.getEmailVerificationStatus(),true,
                true,true,
                authorities);
    }

    private void sendEmail(UserEntity userEntity) throws MessagingException, UnsupportedEncodingException {
        String link = "http://localhost:8080/verification-service/email-verification.html?token="+userEntity.getEmailVerificationToken();
        // SimpleMailMessage msg = new SimpleMailMessage();
        MimeMessage msg = javaMailSender.createMimeMessage();
        // msg.setTo(userEntity.getEmail());
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(userEntity.getEmail()));
        msg.setFrom(new InternetAddress("hcms.server@gmail.com","HCMS Server"));
        msg.setSubject("Complete your registration!");
        msg.setText("Hello There,\nPlease verify your email to login.\n" + link);
        javaMailSender.send(msg);
    }
}
