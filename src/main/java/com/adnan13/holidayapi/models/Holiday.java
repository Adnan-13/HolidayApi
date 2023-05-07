package com.adnan13.holidayapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Holiday {
    private String name;
    private LocalDate date;
    private String type;
}
