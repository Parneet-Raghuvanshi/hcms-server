package com.developer.hcmsserver.services.implementations;

import com.developer.hcmsserver.dto.UserDto;
import com.developer.hcmsserver.entity.PasswordResetTokenEntity;
import com.developer.hcmsserver.entity.UserEntity;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.repository.PasswordResetRequestRepository;
import com.developer.hcmsserver.repository.UserRepository;
import com.developer.hcmsserver.services.interfaces.UserService;
import com.developer.hcmsserver.utils.GeneralUtils;
import com.developer.hcmsserver.utils.SecurityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    PasswordResetRequestRepository passwordResetRequestRepository;

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
            String link = SecurityConstants.getProductionUrl()+"/verify/email-verification/"+userEntity.getEmailVerificationToken();
            String subject = "Complete your registration!";
            String mailContent = "<p>Hello "+userEntity.getFirstName()+" "+userEntity.getLastName()+",</p>";
            mailContent += "<p>Please click Verify Now to complete your registration!";
            mailContent += "<h5><a href=\""+link+"\">Verify Now</a></h5>";
            mailContent += "<p>Thank You,<br>The HCMS Team</p>";
            sendEmail(userEntity.getEmail(),subject,mailContent);
        } catch (Exception e) {
            throw new ServerException(UserServiceException.MAIL_NOT_SENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // and send the UserDto back to Controller
        return mapper.map(savedUserEntity,UserDto.class);
    }

    @Override
    public boolean verifyEmailToken(String token) {
        boolean isTokenVerified = false;
        UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);
        if (userEntity != null) {
            boolean hasTokenExpired = GeneralUtils.hasTokenExpired(token);
            if (!hasTokenExpired) {
                userEntity.setEmailVerificationToken(null);
                userEntity.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(userEntity);
                isTokenVerified = true;
            }
        }
        return isTokenVerified;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        boolean returnValue = false;
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) throw new ServerException(UserServiceException.USER_NOT_FOUND,HttpStatus.INTERNAL_SERVER_ERROR);

        String token = new GeneralUtils().generatePasswordResetToken(userEntity.getUserId());

        PasswordResetTokenEntity passwordTokenEntity = new PasswordResetTokenEntity();
        passwordTokenEntity.setToken(token);
        passwordTokenEntity.setUserDetails(userEntity);
        passwordResetRequestRepository.save(passwordTokenEntity);
        // send email to user
        try {
            String link = SecurityConstants.getProductionUrl()+"/verify/password-reset/"+token;
            String subject = "Please reset your password!";
            String mailContent = "<p>Hello "+userEntity.getFirstName()+" "+userEntity.getLastName()+",</p>";
            mailContent += "<p>Please click Reset Now to reset your login password!";
            mailContent += "<h5><a href=\""+link+"\">Reset Now</a></h5>";
            mailContent += "<p>Thank You,<br>The HCMS Team</p>";
            sendEmail(userEntity.getEmail(),subject,mailContent);
        } catch (Exception e) {
            throw new ServerException(UserServiceException.MAIL_NOT_SENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Override
    public boolean resetPassword(String token, String password) {
        boolean returnValue = false;
        if (GeneralUtils.hasTokenExpired(token)) return false;

        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetRequestRepository.findByToken(token);

        if (passwordResetTokenEntity == null) return false;
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        UserEntity userEntity = passwordResetTokenEntity.getUserDetails();
        userEntity.setEncryptedPassword(encodedPassword);
        UserEntity savedUserEntity = userRepository.save(userEntity);

        if (savedUserEntity.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) returnValue = true;
        passwordResetRequestRepository.delete(passwordResetTokenEntity);
        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findUserByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }

    // By Default for Spring Security
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

    private void sendEmail(String email,String subject,String mailContent) throws MessagingException, UnsupportedEncodingException {
        // LOCALHOST -> for testing purpose only.
        // String local = "http://localhost:8080";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setFrom("hcms.server@gmail.com","HCMS Server");
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(mailContent,true);
        javaMailSender.send(message);
    }
}
