package com.agorton.londonusers.service;

import org.springframework.stereotype.Service;

@Service
public class DistanceService {
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    private static final double LONDON_LAT = 51.509865;
    private static final double LONDON_LONG = -0.118092;

    public double getDistanceFromLondon(double latitude, double longitude) {
        double latitudeDiffInRadians = Math.toRadians((LONDON_LAT - latitude));
        double longitudeDiffInRadians = Math.toRadians(LONDON_LONG - longitude);

        double londonLatInRadians = Math.toRadians(LONDON_LAT);
        double userLatInRadians = Math.toRadians(latitude);

        double a = haversin(latitudeDiffInRadians) + Math.cos(londonLatInRadians) * Math.cos(userLatInRadians) * haversin(longitudeDiffInRadians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return kilometersToMiles(EARTH_RADIUS * c);
    }

    private double kilometersToMiles(double kilometers) {
        return kilometers * 0.62137;
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
