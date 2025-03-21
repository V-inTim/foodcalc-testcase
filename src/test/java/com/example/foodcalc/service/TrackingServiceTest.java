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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackingServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private MealMapper mealMapper;

    @InjectMocks
    private TrackingService trackingService;

    private User user;
    private Meal meal;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setDailyNorm(2000);

        meal = new Meal();
        meal.setId(1L);
        meal.setCalories(500);
        meal.setMealDate(LocalDate.now());
        meal.setUser(user);

        date = LocalDate.now();
    }

    @Test
    void report_UserExists_ReturnsReportDto() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(mealRepository.findByMealDateAndUserId(date, user.getId())).thenReturn(List.of(meal));

        ReportDto report = trackingService.report(user.getId(), date);

        assertNotNull(report);
        assertEquals(500, report.getDailyCalories());
//        verify(userRepository, times(1)).findById(user.getId());
//        verify(mealRepository, times(1)).findByMealDateAndUserId(date, user.getId());
    }

    @Test
    void report_UserDoesNotExist_ThrowsUserException() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> trackingService.report(user.getId(), date));
    }

    @Test
    void check_UserExistsAndCaloriesInNorm_ReturnsCheckDto() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(mealRepository.findByMealDateAndUserId(date, user.getId())).thenReturn(List.of(meal));

        CheckDto check = trackingService.check(user.getId(), date);

        assertNotNull(check);
        assertTrue(check.isInNorm());
    }

    @Test
    void check_UserExistsAndCaloriesExceedNorm_ReturnsCheckDto() {
        meal.setCalories(2500);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(mealRepository.findByMealDateAndUserId(date, user.getId())).thenReturn(List.of(meal));

        CheckDto check = trackingService.check(user.getId(), date);

        assertNotNull(check);
        assertFalse(check.isInNorm());
    }

    @Test
    void check_UserDoesNotExist_ThrowsUserException() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> trackingService.check(user.getId(), date));
    }

    @Test
    void getHistory_UserExists_ReturnsHistoryDto() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(mealRepository.findByUserId(user.getId())).thenReturn(List.of(meal));
        when(mealMapper.mealToDto(meal)).thenReturn(new HistoryMealDto());

        HistoryDto history = trackingService.getHistory(user.getId());

        assertNotNull(history);
        assertEquals(1, history.getHistory().size());
    }
}
