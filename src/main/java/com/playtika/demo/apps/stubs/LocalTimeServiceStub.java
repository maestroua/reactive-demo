package com.playtika.demo.apps.stubs;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import com.playtika.demo.apps.LocalTimeService;
import com.playtika.demo.dto.Location;

import static com.playtika.demo.utils.Utils.log;
import static reactor.core.publisher.Flux.just;

@RequiredArgsConstructor
public class LocalTimeServiceStub implements LocalTimeService {

    private static final Scheduler LOCAL_TIME_IO = Schedulers.newSingle("Local Time IO");

    private final long responseTime;
    private final Map<Location, Date> stub = Map.of(
        Location.NEW_YORK, new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 12),
        Location.SAN_FRANCISCO, new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 16),
        Location.VINNITSIA, new Date()
    );

    @Override
    public Flux<Date> get(Location location) {
        return just(location)
            .doOnNext($ -> log("Local Time requested for {}", location))
            .map(stub::get)
            .delayElements(Duration.ofMillis(responseTime), LOCAL_TIME_IO)
            .doOnNext(time -> log("Local Time in {} is {}", location, time));
    }
}
