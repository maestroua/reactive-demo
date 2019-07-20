package com.playtika.demo.apps.stubs;

import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import com.playtika.demo.apps.WeatherService;
import com.playtika.demo.dto.Location;
import com.playtika.demo.dto.WeatherInfo;

import static reactor.core.publisher.Flux.just;

public class WeatherServiceStub implements WeatherService {

    private static final Scheduler WEATHER_IO = Schedulers.newSingle("Weather IO");

    private final Map<Location, WeatherInfo> stub = Map.of(
        Location.NEW_YORK, new WeatherInfo(30),
        Location.SAN_FRANCISCO, new WeatherInfo(42),
        Location.VINNITSIA, new WeatherInfo(26)
    );

    public Flux<WeatherInfo> getForLocation(Location location) {
        return just(location)
            .map(stub::get)
            .publishOn(WEATHER_IO);
    }
}