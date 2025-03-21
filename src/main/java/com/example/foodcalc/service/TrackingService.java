package com.example.foodcalc.service;

import com.example.foodcalc.dto.CheckDto;
import com.example.foodcalc.dto.HistoryDto;
import com.example.foodcalc.dto.HistoryMealDto;
import com.example.foodcalc.dto.ReportDto;
import com.example.foodcalc.entity.Meal;
import com.example.foodcalc.entity.User;
import com.example.foodcalc.exception.UserException;
import com.example.foodcalc.mapper.MealMapper;
import com.example.foodcalc.repository.MealRepository;
import com.example.foodcalc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrackingService {
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealMapper mealMapper;

    private static final Logger logger = LoggerFactory.getLogger(TrackingService.class);

    @Autowired
    public TrackingService(UserRepository userRepository, MealRepository mealRepository, MealMapper mealMapper) {
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
        this.mealMapper = mealMapper;
    }

    public ReportDto report(long userId, LocalDate date){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            throw new UserException("Пользователь с данным id не существует.");
        User user = optionalUser.get();

        if (date == null)
            date = LocalDate.now();

        List<Meal> meals = mealRepository.findByMealDateAndUserId(date, userId);
        int calories = meals.stream().mapToInt(Meal::getCalories).sum();

        logger.info("Отчет создан.");
        return ReportDto.builder()
                .dailyCalories(calories).build();
    }

    public CheckDto check(long userId, LocalDate date){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            throw new UserException("Пользователь с данным id не существует.");
        User user = optionalUser.get();

        if (date == null)
            date = LocalDate.now();

        List<Meal> meals = mealRepository.findByMealDateAndUserId(date, userId);
        int calories = meals.stream().mapToInt(Meal::getCalories).sum();

        logger.info("Проверка проведена.");
        return CheckDto.builder()
                .isInNorm(calories <= user.getDailyNorm()).build();
    }

    public HistoryDto getHistory(long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            throw new UserException("Пользователь с данным id не существует.");
        User user = optionalUser.get();

        List<Meal> meals = mealRepository.findByUserId(userId);
        Map<String, List<HistoryMealDto>> history = meals.stream()
                .collect(Collectors.groupingBy(
                        meal -> meal.getMealDate().toString(),
                        Collectors.mapping(mealMapper::mealToDto, Collectors.toList())
                ));

        logger.info("История собрана.");
        return HistoryDto.builder()
                .history(history).build();
    }


}
