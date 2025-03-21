package com.example.foodcalc.controller;

import com.example.foodcalc.dto.*;
import com.example.foodcalc.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foodcalc")
public class UserController {
    private  final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserDto dto){
        logger.info("Запрос на user");

        UserResponseDto userResponseDto = userService.addUser(dto);

        logger.info("Ответ на user");
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PostMapping("/dish")
    public ResponseEntity<DishResponseDto> addDish(@Valid @RequestBody  DishDto dto){
        logger.info("Запрос на dish");

        DishResponseDto dishResponseDto = userService.addDish(dto);

        logger.info("Ответ на dish");
        return new ResponseEntity<>(dishResponseDto, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/meal")
    public ResponseEntity<MealResponseDto> addMeal(@Valid @RequestBody MealDto dto, @PathVariable long userId){
        logger.info("Запрос на meal");

        MealResponseDto mealResponseDto = userService.addMeal(dto, userId);

        logger.info("Ответ на meal");
        return new ResponseEntity<>(mealResponseDto, HttpStatus.OK);
    }

}
