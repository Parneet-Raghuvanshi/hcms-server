package com.developer.hcmsserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "users_main_id")
    private UserEntity userDetails;
}
