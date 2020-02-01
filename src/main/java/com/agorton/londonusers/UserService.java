package com.agorton.londonusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findWithinMilesOfLondon(int numberOfMiles) {
        List<User> usersFromApi = userRepository.getUsersFromApi();
        return usersFromApi.stream()
                .filter(user -> getDistanceFromLondon(user) < numberOfMiles)
                .collect(Collectors.toList());
    }

    private double getDistanceFromLondon(User user) {
        double londonLat = 51.509865;
        double londonLong = -0.118092;

        double userLat = user.latitude;
        double userLong = user.longitude;

        double latitudeDiffInRadians = Math.toRadians((londonLat - userLat));
        double longitudeDiffInRadians = Math.toRadians(londonLong - userLong);

        double londonLatInRadians = Math.toRadians(londonLat);
        double userLatInRadians = Math.toRadians(user.latitude);

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
