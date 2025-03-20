package com.example.foodcalc.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishDto {
    @NotNull
    private String name;

    @NotNull
    @Min(value = 0, message = "Поле caloriesPerServing не может быть отрицательным.")
    private int caloriesPerServing;

    @NotNull
    @Valid
    MealPfcDto pfc;
}
