package com.example.demo.repository.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "LOCATION")
public class Location implements Serializable {

    @Id
    @Column(name = "CODE", length = 10)
    private String code;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "COUNTRY", length = 50)
    private String country;

    @Column(name = "CITY", length = 50)
    private String city;

}
