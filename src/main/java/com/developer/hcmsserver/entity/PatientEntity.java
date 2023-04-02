package com.developer.hcmsserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity(name = "patient")
public class PatientEntity implements Serializable {
    //---(Server => Variables)---//
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String publicId;
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_disease",
            joinColumns = {@JoinColumn(name = "patient_id")},
            inverseJoinColumns = {@JoinColumn(name = "disease_id")}
    )
    private List<DiseaseEntity> diseases;
    @OneToMany( mappedBy = "patient", cascade = CascadeType.ALL)
    private List<AppointmentEntity> appointments;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AlertEntity> alerts;
}
