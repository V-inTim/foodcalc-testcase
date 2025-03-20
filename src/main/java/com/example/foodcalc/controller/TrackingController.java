package com.example.foodcalc.controller;

import com.example.foodcalc.dto.CheckDto;
import com.example.foodcalc.dto.HistoryDto;
import com.example.foodcalc.dto.ReportDto;
import com.example.foodcalc.service.TrackingService;
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

    @Autowired
    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/report")
    public ResponseEntity<ReportDto> report(@PathVariable long userId,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        ReportDto reportDto = trackingService.report(userId, date);
        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<CheckDto> checkDailyNorm(@PathVariable long userId,
                                          @RequestParam(required = false)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        CheckDto checkDto = trackingService.check(userId, date);
        return new ResponseEntity<>(checkDto, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<HistoryDto> getHistory(@PathVariable long userId){
        HistoryDto historyDto = trackingService.getHistory(userId);
        return new ResponseEntity<>(historyDto, HttpStatus.OK);
    }

}
