package com.playtika.demo.apps.stubs;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import com.playtika.demo.apps.LocationService;
import com.playtika.demo.dto.Location;

import static com.playtika.demo.utils.Utils.log;

@RequiredArgsConstructor
public class StaticLocationService implements LocationService {

    private static final Scheduler LOCATION_IO = Schedulers.newSingle("Location IO");

    private final long responseTime;
    private final Location location;

    public Flux<Location> getMyLocation() {
        return Mono.fromRunnable(() -> log("Current location is requested"))
            .thenReturn(location)
            .flux()
            .delayElements(Duration.ofMillis(responseTime), LOCATION_IO)
            .doOnNext(location -> log("Current location is {}", location));
    }
}
