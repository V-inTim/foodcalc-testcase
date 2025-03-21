package com.example.foodcalc.mapper;

import com.example.foodcalc.dto.UserDto;
import com.example.foodcalc.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User dtoToUser(UserDto dto);
}
