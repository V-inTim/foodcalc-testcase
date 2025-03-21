package com.example.foodcalc.dto;

import com.example.foodcalc.type.Gender;
import com.example.foodcalc.type.WeightStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    @Min(value = 0, message = "Возраст не может быть отрицательным.")
    @Max(value = 150, message = "Возраст не может быть таким большим.")
    private short age;

    @NotNull
    private Gender gender;

    @NotNull
    @Min(value = 0, message = "Масса не может быть отрицательной.")
    @Max(value = 700, message = "Масса не может быть такой большой.")
    private short weight;

    @NotNull
    @Min(value = 0, message = "Рост не может быть отрицательным.")
    @Max(value = 300, message = "Рост не может быть таким большим.")
    private short height;

    @NotNull
    private WeightStatus purpose;
}
