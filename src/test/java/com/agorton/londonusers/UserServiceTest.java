package com.agorton.londonusers;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    UserRepository userRepository = mock(UserRepository.class);

    UserService userService = new UserService(userRepository);

    @Test
    void givenUserIsWithinRange_shouldReturnUser() {
        User exampleUser = User.builder().latitude(51.509865).longitude(-0.118092).build();

        when(userRepository.getUsersFromApi()).thenReturn(Lists.newArrayList(exampleUser));

        List<User> withinMilesOfLondon = userService.findWithinMilesOfLondon(50);

        assertThat(withinMilesOfLondon.size() == 1);

    }

    @Test
    void givenUserIsOutsideRange_shouldNotReturnUser() {
        User exampleUser = User.builder().latitude(0d).longitude(0d).build();

        when(userRepository.getUsersFromApi()).thenReturn(Lists.newArrayList(exampleUser));

        List<User> withinMilesOfLondon = userService.findWithinMilesOfLondon(50);

        assertThat(withinMilesOfLondon.size() == 0);

    }
}