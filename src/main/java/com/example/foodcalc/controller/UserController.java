package com.example.foodcalc.controller;

import com.example.foodcalc.dto.DishDto;
import com.example.foodcalc.dto.DishResponseDto;
import com.example.foodcalc.dto.UserDto;
import com.example.foodcalc.dto.UserResponseDto;
import com.example.foodcalc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foodcalc")
public class UserController {
    private  final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserDto dto){
        UserResponseDto userResponseDto = userService.addUser(dto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PostMapping("/dish")
    public ResponseEntity<DishResponseDto> addDish(@Valid @RequestBody  DishDto dto){
        DishResponseDto dishResponseDto = userService.addDish(dto);
        return new ResponseEntity<>(dishResponseDto, HttpStatus.OK);
    }

    @PostMapping("/meal")
    public ResponseEntity<UserResponseDto> addMeal(@Valid @RequestBody UserDto dto){
        UserResponseDto userResponseDto = userService.addUser(dto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

}
