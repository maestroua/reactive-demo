package com.playtika.demo;

import java.time.Duration;

import com.playtika.demo.apps.stubs.FailingWeatherService;
import com.playtika.demo.apps.stubs.LocalTimeServiceStub;
import com.playtika.demo.apps.stubs.SlowWeatherService;
import com.playtika.demo.apps.stubs.StaticLocationService;
import com.playtika.demo.apps.stubs.WeatherServiceStub;
import com.playtika.demo.connection.Connection;
import com.playtika.demo.dto.Location;

import static com.playtika.demo.utils.Utils.log;
import static reactor.core.publisher.Mono.delay;

/**
 * For demo we do not use actual network calls with nio thread,
 * but all services is simulated to work exactly as one for external viewer.
 * Also - in API we use Flux only, but should use Mono, as only one element can be returned for each call.
 * It is done to simplify demo and not show conversions Flux -> Mono, and vice-versa.
 */
public class Application {

    public static void main(String[] args) {
        var weatherService = new WeatherServiceStub();

        var demo = new DemoRequestHandler(
            new StaticLocationService(100, Location.VINNITSIA),
            new SlowWeatherService(500, weatherService),
            new FailingWeatherService(50, weatherService),
            new LocalTimeServiceStub(200)
        );

        long start = System.currentTimeMillis();
        demo.handle(new Connection());
        log("Main thread is now free and he was running for " + (System.currentTimeMillis() - start) + " ms");

        delay(Duration.ofSeconds(20))
            .doOnNext($ -> System.out.println("Bye Bye!"))
            .subscribe($ -> System.exit(42));
    }
}
