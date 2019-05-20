package com.sharepool.server.logic.tour;

import com.sharepool.server.dal.AppUserRepository;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.domain.AppUser;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.rest.tour.dto.TourCreationDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TourRestRequestHandlerTest {

    @Autowired
    private TourRestRequestHandler tourRestRequestHandler;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void getAllToursForUser() {
        AppUser user = appUserRepository.save(createUser());

        TourCreationDto validTourCreationDto = createValidTourCreationDto();
        validTourCreationDto.setOwnerId(user.getId());

        tourRestRequestHandler.createTour(validTourCreationDto);

        List<Tour> allToursForUser = tourRestRequestHandler.getAllToursForUser(user.getId());

        Assert.assertEquals(1, allToursForUser.size());
        Assert.assertEquals(validTourCreationDto.getFrom(), allToursForUser.get(0).getFromLocation());
    }

    private TourCreationDto createValidTourCreationDto() {
        TourCreationDto tourCreationDto = new TourCreationDto();
        tourCreationDto.setFrom("Linz");
        tourCreationDto.setTo("Hagenberg");
        tourCreationDto.setCurrency("EUR");
        tourCreationDto.setCost(1);
        tourCreationDto.setKilometers(30);
        tourCreationDto.setOwnerId(1L);
        return tourCreationDto;
    }

    private AppUser createUser() {
        AppUser appUser = new AppUser();
        appUser.setUserName("username");
        appUser.setFirstName("First");
        appUser.setLastName("Last");
        appUser.setPasswordHash("test");
        return appUser;
    }

    @Test
    public void updateTour() {
        AppUser user = appUserRepository.save(createUser());
        Tour tour = tourRepository.save(createTour(user));

        TourCreationDto validTourCreationDto = createValidTourCreationDto();
        validTourCreationDto.setCost(30);

        tourRestRequestHandler.updateTour(tour.getId(), validTourCreationDto);

        Optional<Tour> optionalTour = tourRepository.findById(tour.getId());

        Assert.assertTrue(optionalTour.isPresent());
        Assert.assertEquals(30, optionalTour.get().getTourCost(), 0);
    }

    private Tour createTour(AppUser savedUser) {
        Tour tour = new Tour();
        tour.setFromLocation("Linz");
        tour.setToLocation("Hagenberg");
        tour.setCurrency(Currency.getInstance("EUR"));
        tour.setTourCost(1);
        tour.setKilometers(30);
        tour.setOwner(savedUser);
        return tour;
    }
}