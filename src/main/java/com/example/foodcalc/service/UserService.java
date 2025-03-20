package com.example.foodcalc.service;

import com.example.foodcalc.dto.*;
import com.example.foodcalc.entity.Dish;
import com.example.foodcalc.entity.Meal;
import com.example.foodcalc.entity.User;
import com.example.foodcalc.exception.UserException;
import com.example.foodcalc.mapper.DishMapper;
import com.example.foodcalc.mapper.UserMapper;
import com.example.foodcalc.repository.DishRepository;
import com.example.foodcalc.repository.MealRepository;
import com.example.foodcalc.repository.UserRepository;
import com.example.foodcalc.type.Gender;
import com.example.foodcalc.type.WeightStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final MealRepository mealRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper,
                       DishRepository dishRepository, DishMapper dishMapper,
                       MealRepository mealRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
        this.mealRepository = mealRepository;
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

    public DishResponseDto addDish(DishDto dto){
        Dish dish = dishMapper.dtoToDish(dto);

        dish = dishRepository.save(dish);
        return DishResponseDto.builder()
                .dishId(dish.getId()).build();
    }

    public MealResponseDto addMeal(MealDto dto, Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            throw new UserException("Пользователь с данным id не существует.");
        User user = optionalUser.get();

        List<Dish> dishes = new ArrayList<>();
        int calories = 0;
        for (long dishId: dto.getDishes()){
            Optional<Dish> optionalDish = dishRepository.findById(dishId);
            if (optionalDish.isEmpty())
                throw new UserException(String.format("Ресурс с данным id %d не существует.", dishId));
            calories += optionalDish.get().getCaloriesPerServing();
            dishes.add(optionalDish.get());
        }

        Meal meal = Meal.builder()
                .mealDate(LocalDate.now())
                .dishes(dishes)
                .calories(calories)
                .user(user).build();

        meal = mealRepository.save(meal);
        return MealResponseDto.builder()
                .mealId(meal.getId()).build();
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
