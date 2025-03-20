package com.example.foodcalc.mapper;

import com.example.foodcalc.dto.DishDto;
import com.example.foodcalc.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {MealPfcMapper.class})
public interface DishMapper {
    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);

    Dish dtoToDish(DishDto dto);
}
