package com.playtika.demo.apps;

import java.util.Date;

import reactor.core.publisher.Flux;

import com.playtika.demo.dto.Location;

public interface LocalTimeService {

    Flux<Date> get(Location location);
}
