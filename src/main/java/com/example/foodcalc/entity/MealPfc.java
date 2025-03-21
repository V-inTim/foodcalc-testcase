package com.example.foodcalc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPfc {
    private int proteins;
    private int fats;
    private int carbohydrates;
}
