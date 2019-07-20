package com.playtika.demo.apps;

import reactor.core.publisher.Flux;

import com.playtika.demo.dto.Location;

public interface LocationService {

    Flux<Location> getMyLocation();
}
