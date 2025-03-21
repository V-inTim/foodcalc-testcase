package com.example.foodcalc.entity;

import com.example.foodcalc.type.Gender;
import com.example.foodcalc.type.WeightStatus;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    private short age;

    @Column(name = "user_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private short weight;

    private short height;

    @Enumerated(EnumType.STRING)
    private WeightStatus purpose;

    @Column(name = "daily_norm")
    private int dailyNorm;

}
