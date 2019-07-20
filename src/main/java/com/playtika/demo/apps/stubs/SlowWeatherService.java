package com.playtika.demo.apps.stubs;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import com.playtika.demo.apps.WeatherService;
import com.playtika.demo.dto.Location;
import com.playtika.demo.dto.WeatherInfo;

import static com.playtika.demo.utils.Utils.log;
import static reactor.core.publisher.Mono.fromRunnable;

@RequiredArgsConstructor
public class SlowWeatherService implements WeatherService {
    private static final Scheduler SLOW_IO = Schedulers.newSingle("Slow Weather IO");

    private final long responseTime;
    private final WeatherService original;

    @Override
    public Flux<WeatherInfo> getForLocation(Location location) {
        return fromRunnable(() -> log("Slow Weather service requested for {}", location))
            .thenMany(original.getForLocation(location))
            .delayElements(Duration.ofMillis(responseTime), SLOW_IO)
            .doOnNext(weather -> log("Weather in {} is {}", location, weather));
    }
}
