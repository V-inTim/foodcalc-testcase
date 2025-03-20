package com.example.foodcalc.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "calories_per_serving")
    private int caloriesPerServing;

    @Column(name = "pfc", columnDefinition = "jsonb")
    @Type(JsonType.class)
    private MealPFC pfc;
}
