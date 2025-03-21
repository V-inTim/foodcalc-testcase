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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishMapper dishMapper;

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private User user;
    private DishDto dishDto;
    private Dish dish;
    private MealDto mealDto;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        userDto = UserDto.builder()
                .height((short) 180)
                .weight((short) 80)
                .age((short) 30)
                .gender(Gender.MALE)
                .purpose(WeightStatus.WEIGHT_MAINTENANCE)
                .build();

        user = new User();
        user.setId(1L);
        user.setDailyNorm(2500);

        dishDto = DishDto.builder()
                .name("Pasta")
                .caloriesPerServing(500)
                .build();

        dish = new Dish();
        dish.setId(1L);
        dish.setCaloriesPerServing(500);

        mealDto = MealDto.builder()
                .dishes(List.of(1L))
                .build();
    }

    @Test
    void addUser_ValidInput_ReturnsUserResponseDto() {
        when(userMapper.dtoToUser(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        UserResponseDto response = userService.addUser(userDto);

        assertNotNull(response);
        assertEquals(user.getId(), response.getUserId());
        assertEquals(user.getDailyNorm(), response.getDailyNorm());
    }

    @Test
    void addDish_ValidInput_ReturnsDishResponseDto() {
        when(dishMapper.dtoToDish(dishDto)).thenReturn(dish);
        when(dishRepository.save(dish)).thenReturn(dish);

        DishResponseDto response = userService.addDish(dishDto);

        assertNotNull(response);
        assertEquals(dish.getId(), response.getDishId());
    }

    @Test
    void addMeal_ValidInput_ReturnsMealResponseDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> {
            Meal meal = invocation.getArgument(0);
            meal.setId(1L); // Симуляция сохранения с присвоением ID
            return meal;
        });

        MealResponseDto response = userService.addMeal(mealDto, 1L);

        assertNotNull(response);
        assertEquals(1L, response.getMealId());
    }

    @Test
    void addMeal_UserNotFound_ThrowsUserException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> userService.addMeal(mealDto, 1L));
    }

    @Test
    void addMeal_DishNotFound_ThrowsUserException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> userService.addMeal(mealDto, 1L));
    }

}