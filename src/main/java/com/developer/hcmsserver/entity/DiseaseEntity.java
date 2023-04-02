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
@Entity(name = "disease")
public class DiseaseEntity implements Serializable {
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
    private String description;
    @Column(nullable = false)
    private String img;
    @Column(nullable = false)
    private String severity;
    //---(Relational => Variables)---//
    @ManyToMany(mappedBy = "diseases")
    private List<PatientEntity> patients;
}
