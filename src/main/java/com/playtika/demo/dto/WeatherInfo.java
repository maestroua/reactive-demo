package com.playtika.demo.dto;

import lombok.Value;

@Value
public class WeatherInfo {
    public int temperature;

    @Override
    public String toString() {
        return temperature + "C";
    }
}