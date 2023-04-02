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
@Entity(name = "hospital")
public class HospitalEntity implements Serializable {
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
    private String rating;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private AddressEntity address;
    //---(Relational => Variables)---//
    @OneToMany( mappedBy = "hospital")
    private List<DoctorEntity> doctors;
}
