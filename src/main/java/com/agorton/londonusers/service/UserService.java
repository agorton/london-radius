package com.agorton.londonusers.service;

import com.agorton.londonusers.domain.User;
import com.agorton.londonusers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private DistanceService distanceService;

    @Autowired
    public UserService(UserRepository userRepository, DistanceService distanceService) {
        this.userRepository = userRepository;
        this.distanceService = distanceService;
    }

    public List<User> findAllUsersWithinMilesOfLondon(int numberOfMiles) {
        List<User> usersWithinRadiusOfLondon = findWithinMilesOfLondon(numberOfMiles);
        List<User> usersLivingInLondon = findAllUsersWithCity("London");

        Set<User> allUsers = new HashSet<>();
        allUsers.addAll(usersLivingInLondon);
        allUsers.addAll(usersWithinRadiusOfLondon);

        return new ArrayList<>(allUsers);
    }

    public List<User> findWithinMilesOfLondon(int numberOfMiles) {
        List<User> usersFromApi = userRepository.getAllUsers();
        return usersFromApi.stream()
                .filter(user -> distanceService.getDistanceFromLondon(user.latitude, user.longitude) < numberOfMiles)
                .collect(Collectors.toList());
    }

    public List<User> findAllUsersWithCity(String city) {
        return userRepository.getUsersForCity(city);
    }

}
