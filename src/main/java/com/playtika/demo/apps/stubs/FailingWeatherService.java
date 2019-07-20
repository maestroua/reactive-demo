package com.playtika.demo.apps.stubs;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import com.playtika.demo.apps.WeatherService;
import com.playtika.demo.dto.Location;
import com.playtika.demo.dto.WeatherInfo;

import static com.playtika.demo.utils.Utils.log;
import static reactor.core.publisher.Flux.defer;
import static reactor.core.publisher.Flux.error;
import static reactor.core.publisher.Mono.fromRunnable;

@RequiredArgsConstructor
public class FailingWeatherService implements WeatherService {

    private final int percent; // service will fail in percent% of cases
    private final WeatherService original;

    @Override
    public Flux<WeatherInfo> getForLocation(Location location) {
        return fromRunnable(() -> log("Failing Weather service requested for {}", location))
            .thenMany(original.getForLocation(location))
            .filter($ -> Math.random() * 100 < percent)
            .switchIfEmpty(defer(() -> error(new RuntimeException("Weather Service is unavailable."))));
    }
}