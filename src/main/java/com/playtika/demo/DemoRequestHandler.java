package com.playtika.demo;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import com.playtika.demo.apps.LocalTimeService;
import com.playtika.demo.apps.LocationService;
import com.playtika.demo.apps.WeatherService;
import com.playtika.demo.connection.Connection;
import com.playtika.demo.dto.Location;
import com.playtika.demo.dto.WeatherInfo;

import static com.playtika.demo.utils.Utils.log;

@RequiredArgsConstructor
public class DemoRequestHandler {

    private static final Scheduler CLIENT_IO = Schedulers.newSingle("Client IO");

    private final LocationService locationService;

    private final WeatherService slowWeatherService;
    private final WeatherService failingWeatherService;

    private final LocalTimeService localTimeService;

    public void handle(Connection connection) {
        locationService.getMyLocation()
            .flatMap(location -> getWeather(location)
                .zipWith(localTimeService.get(location))
                .map(weather -> Tuples.of(location, weather)))
            .delaySequence(Duration.ofMillis(500))
            .repeat(10)
            .publishOn(CLIENT_IO)
            .timeout(Duration.ofSeconds(10))
            .subscribe(connection::write);
    }

    private Flux<WeatherInfo> getWeather(Location location) {
        return failingWeatherService.getForLocation(location)
            .doOnError(ex -> log("Weather service failed {}", ex))
            .retry(1)
            .onErrorResume(ex -> slowWeatherService.getForLocation(location));
    }
}