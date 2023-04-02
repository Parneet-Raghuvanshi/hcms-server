package com.developer.hcmsserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "appointment")
public class AppointmentEntity implements Serializable {
    //---(Server => Variables)---//
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String publicId;
    //---(Main => Variables)---//
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String type;
    //---(Relational => Variables)---//
    @ManyToOne
    @JoinColumn
    private PatientEntity patient;
    @ManyToOne
    @JoinColumn
    private DoctorEntity doctor;
}
