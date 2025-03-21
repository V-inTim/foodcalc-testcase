package com.example.foodcalc.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealPfcDto {
    @NotNull
    @Min(value = 0, message = "Поле proteins не может быть отрицательным.")
    private int proteins;

    @NotNull
    @Min(value = 0, message = "Поле fats не может быть отрицательным.")
    private int fats;

    @NotNull
    @Min(value = 0, message = "Поле carbohydrates не может быть отрицательным.")
    private int carbohydrates;
}
