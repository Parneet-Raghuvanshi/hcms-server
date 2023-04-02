package com.developer.hcmsserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity(name = "alert")
public class AlertEntity implements Serializable {
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
    private String level;
    @Column(nullable = false)
    private String description;
}
