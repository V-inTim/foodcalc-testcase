package com.example.foodcalc.dto;

import com.example.foodcalc.entity.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryMealDto {
    private long id;
    private int calories;
    private List<Dish> dishes;
}
