package com.agorton.londonusers.service;

import com.agorton.londonusers.domain.User;
import com.agorton.londonusers.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private DistanceService distanceService = new DistanceService();

    UserService userService = new UserService(userRepository, distanceService);

    @Test
    void givenUserIsWithinRange_shouldReturnUser() {
        User exampleUser = User.builder().latitude(51.509865).longitude(-0.118092).build();

        when(userRepository.getAllUsers()).thenReturn(Lists.newArrayList(exampleUser));

        List<User> withinMilesOfLondon = userService.findWithinMilesOfLondon(50);

        assertEquals(withinMilesOfLondon.size(), 1);
    }

    @Test
    void givenUserIsOutsideRange_shouldNotReturnUser() {
        User exampleUser = User.builder().latitude(0d).longitude(0d).build();

        when(userRepository.getAllUsers()).thenReturn(Lists.newArrayList(exampleUser));

        List<User> withinMilesOfLondon = userService.findWithinMilesOfLondon(50);

        assertEquals(withinMilesOfLondon.size(), 0);
    }

    @Test
    void givenSameUserIsReturnedFromBothEndpoints_shouldNotContainDuplicates(){
        User exampleUser = User.builder().latitude(51.509865).longitude(-0.118092).build();

        when(userRepository.getAllUsers()).thenReturn(Lists.newArrayList(exampleUser));
        when(userRepository.getUsersForCity("London")).thenReturn(Lists.newArrayList(exampleUser));

        List<User> withinMilesOfLondon = userService.findAllUsersWithinMilesOfLondon(50);

        assertEquals(withinMilesOfLondon.size(), 1);
    }
}