package com.example.foodcalc.service;

import com.example.foodcalc.dto.UserDto;
import com.example.foodcalc.dto.UserResponseDto;
import com.example.foodcalc.entity.User;
import com.example.foodcalc.mapper.UserMapper;
import com.example.foodcalc.repository.UserRepository;
import com.example.foodcalc.type.Gender;
import com.example.foodcalc.type.WeightStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDto addUser(UserDto dto){
        int dailyNorm = calcDailyNorm(dto.getHeight(), dto.getWeight(), dto.getAge(),
                dto.getGender(), dto.getPurpose());
        User user = userMapper.dtoToUser(dto);
        user.setDailyNorm(dailyNorm);

        user = userRepository.save(user);
        return UserResponseDto.builder()
                .userId(user.getId())
                .dailyNorm(dailyNorm).build();
    }

    private int calcDailyNorm(Short height, Short weight, Short age,
                              Gender gender, WeightStatus purpose){
        double bmr;

        if (gender.equals(Gender.MALE)) {
            bmr = 88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age);
        } else {
            bmr = 447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age);
        }

        double dailyCalories = bmr * 1.2;

        return switch (purpose) {
            case WEIGHT_LOSS -> (int) (dailyCalories * 0.85);
            case WEIGHT_MAINTENANCE -> (int) dailyCalories;
            case WEIGHT_GAIN -> (int) (dailyCalories * 1.15);
        };
    }
}
