package com.developer.hcmsserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.print.Doc;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity(name = "user")
public class UserEntity implements Serializable {
    //---(Server => Variables)---//
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String publicId;
    //---(Main => Variables)---//
    @Column(nullable = false,length = 50)
    private String name;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false,length = 120)
    private String email;
    //---(Encryption - Security  => Variables)---//
    @Column(nullable = false)
    private String encryptedPassword;
    @Column
    private String emailVerificationToken;
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;
}
