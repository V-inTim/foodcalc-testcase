package com.example.foodcalc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "meal_date")
    private LocalDate mealDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int calories;

    @ManyToMany
    @JoinTable(
            name = "meal_dishes",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;
}