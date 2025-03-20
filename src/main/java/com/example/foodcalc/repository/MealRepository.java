package com.example.foodcalc.repository;

import com.example.foodcalc.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {

}
