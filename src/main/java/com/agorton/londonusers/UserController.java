package com.agorton.londonusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//TODO: combine user sources into one repo call.

@RestController
public class UserController {
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsersInCity(@RequestParam String city) {
        return userRepository.getUsersForCity(city);
    }

    @GetMapping("/users/api")
    public List<User> getUsersFromApi(@RequestParam(required = false) Integer withinDistance) {
        if (withinDistance != null) {
            return userService.findWithinMilesOfLondon(withinDistance);
        } else {
            return userRepository.getUsersFromApi();
        }
    }
}
