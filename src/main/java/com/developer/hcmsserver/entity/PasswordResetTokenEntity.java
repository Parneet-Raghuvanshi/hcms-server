package com.developer.hcmsserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String token;
    @OneToOne
    @JoinColumn
    private UserEntity user;
}
