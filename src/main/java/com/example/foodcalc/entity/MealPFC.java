package com.example.foodcalc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealPFC {
    private int proteins;
    private int fats;
    private int carbohydrates;
}
