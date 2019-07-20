package com.playtika.demo.dto;

import lombok.Value;

@Value
public class Location {
    public static final Location NEW_YORK = new Location("New York", 40000, 544234);
    public static final Location SAN_FRANCISCO = new Location("San Francisco", 31, 32131);
    public static final Location VINNITSIA = new Location("Vinnitsia", 123, 323131);
    public static final Location BROKEN = new Location("Unknown location", 123, 323131);

    public String name;
    public long longitude;
    public long latitude;

    @Override
    public String toString() {
        return name;
    }
}
