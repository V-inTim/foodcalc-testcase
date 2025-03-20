package com.example.foodcalc.repository;

import com.example.foodcalc.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByMealDateAndUserId(LocalDate mealDate, Long userId);
    List<Meal> findByUserId(Long userId);
}
