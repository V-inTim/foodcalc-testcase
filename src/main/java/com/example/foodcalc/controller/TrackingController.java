package com.example.foodcalc.controller;

import com.example.foodcalc.dto.CheckDto;
import com.example.foodcalc.dto.HistoryDto;
import com.example.foodcalc.dto.ReportDto;
import com.example.foodcalc.service.TrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/foodcalc/user/{userId}")
public class TrackingController {
    private final TrackingService trackingService;

    private static final Logger logger = LoggerFactory.getLogger(TrackingController.class);

    @Autowired
    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/report")
    public ResponseEntity<ReportDto> report(@PathVariable long userId,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        logger.info("Запрос на report");

        ReportDto reportDto = trackingService.report(userId, date);

        logger.info("Ответ на report");
        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<CheckDto> checkDailyNorm(@PathVariable long userId,
                                          @RequestParam(required = false)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        logger.info("Запрос на check");

        CheckDto checkDto = trackingService.check(userId, date);

        logger.info("Ответ на check");
        return new ResponseEntity<>(checkDto, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<HistoryDto> getHistory(@PathVariable long userId){
        logger.info("Запрос на history");

        HistoryDto historyDto = trackingService.getHistory(userId);

        logger.info("Ответ на history");
        return new ResponseEntity<>(historyDto, HttpStatus.OK);
    }

}
