package com.developer.hcmsserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Entity(name = "doctor")
public class DoctorEntity implements Serializable {
    //---(Server => Variables)---//
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String publicId;
    //---(Main => Variables)---//
    @Column
    private String speciality;
    @Column
    private String rating;
    //---(Relational => Variables)---//
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private UserEntity user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private CommonEntity common;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private AddressEntity address;
    @OneToMany( mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<AppointmentEntity> appointments;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AlertEntity> alerts;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private HospitalEntity hospital;
}
