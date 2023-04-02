package com.developer.hcmsserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity(name = "common")
public class CommonEntity implements Serializable {
    //---(Server => Variables)---//
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String publicId;
    //---(Main => Variables)---//
    @Column
    private String age;
    @Column
    private String contact;
    @Column
    private String height;
    @Column
    private String weight;
    @Column
    private String spo2;
    @Column
    private String bp;
    @Column
    private String glucose;
}
