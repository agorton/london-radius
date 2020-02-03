package com.agorton.londonusers.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistanceServiceTest {

    @Test
    void givenLatLong_shouldReturnDistanceFromLondon() {
        DistanceService distanceService = new DistanceService();

        double distanceFromLondonInMiles = distanceService.getDistanceFromLondon(0d, 0d);

        assertEquals(3558.987656275401, distanceFromLondonInMiles);
    }
}