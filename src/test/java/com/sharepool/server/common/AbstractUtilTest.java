package com.sharepool.server.common;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.user.dto.UserDto;

import java.util.Currency;

public abstract class AbstractUtilTest {

    private static int ID = 0;

    protected User createValidUser() {
        User user = new User();
        user.setUserName("username" + ++ID);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setEmail("email" + ++ID + "@test.com");
        user.setPasswordHash("test");
        return user;
    }

    protected Tour createValidTour(User savedUser) {
        Tour tour = new Tour();
        tour.setFromLocation("Linz");
        tour.setToLocation("Hagenberg");
        tour.setCurrency(Currency.getInstance("EUR"));
        tour.setTourCost(1);
        tour.setKilometers(30);
        tour.setOwner(savedUser);
        return tour;
    }

    protected TourDto createValidTourDto() {
        TourDto tourDto = new TourDto();
        tourDto.setFrom("Linz");
        tourDto.setTo("Hagenberg");
        tourDto.setCurrency("EUR");
        tourDto.setCost(1);
        tourDto.setKilometers(30);
        return tourDto;
    }

    protected UserDto createValidUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstname");
        userDto.setLastName("lastname");
        userDto.setUserName("userName");
        userDto.setEmail("test@email.com");
        userDto.setPassword("password");
        return userDto;
    }
}
