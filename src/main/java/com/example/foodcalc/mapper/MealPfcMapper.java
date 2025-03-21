package com.example.foodcalc.mapper;

import com.example.foodcalc.dto.MealPfcDto;
import com.example.foodcalc.entity.MealPfc;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MealPfcMapper {
    MealPfcMapper INSTANCE = Mappers.getMapper(MealPfcMapper.class);

    MealPfc dtoToDish(MealPfcDto dto);
}
