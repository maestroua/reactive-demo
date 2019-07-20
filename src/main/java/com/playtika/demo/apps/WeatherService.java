package com.playtika.demo.apps;

import reactor.core.publisher.Flux;

import com.playtika.demo.dto.Location;
import com.playtika.demo.dto.WeatherInfo;

public interface WeatherService {

    Flux<WeatherInfo> getForLocation(Location location);
}
