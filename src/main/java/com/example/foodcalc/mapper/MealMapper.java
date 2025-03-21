package com.example.foodcalc.mapper;

import com.example.foodcalc.dto.HistoryMealDto;
import com.example.foodcalc.entity.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MealMapper {
    MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

    HistoryMealDto mealToDto(Meal meal);
}
