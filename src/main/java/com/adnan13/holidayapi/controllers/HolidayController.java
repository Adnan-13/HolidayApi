package com.adnan13.holidayapi.controllers;

import com.adnan13.holidayapi.models.Holiday;
import com.adnan13.holidayapi.services.HolidayService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class HolidayController {
    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("/holidays/{year}")
    public ResponseEntity<?> getHolidays(@PathVariable int year) throws IOException, IOException {
        List<Holiday> holidays = holidayService.getHolidays(year);
        return ResponseEntity.ok(holidays);
    }

    @GetMapping("/is-holiday/{date}")
    public ResponseEntity<?> isHoliday(@PathVariable @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date) throws IOException {
        boolean isHoliday = holidayService.isHoliday(date);
        return ResponseEntity.ok(isHoliday);
    }
}
