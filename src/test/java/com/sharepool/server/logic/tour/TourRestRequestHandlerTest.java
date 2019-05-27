package com.sharepool.server.logic.tour;

import com.sharepool.server.dal.AppUserRepository;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.domain.AppUser;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.rest.tour.dto.TourDto;
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

        TourDto validTourDto = createValidTourCreationDto();
        validTourDto.setOwnerId(user.getId());

        tourRestRequestHandler.createTour(validTourDto);

        List<TourDto> allToursForUser = tourRestRequestHandler.getAllToursForUser(user.getId());

        Assert.assertEquals(1, allToursForUser.size());
        Assert.assertEquals(validTourDto.getFrom(), allToursForUser.get(0).getFrom());
    }

    private TourDto createValidTourCreationDto() {
        TourDto tourDto = new TourDto();
        tourDto.setFrom("Linz");
        tourDto.setTo("Hagenberg");
        tourDto.setCurrency("EUR");
        tourDto.setCost(1);
        tourDto.setKilometers(30);
        tourDto.setOwnerId(1L);
        return tourDto;
    }

    private AppUser createUser() {
        AppUser appUser = new AppUser();
        appUser.setUsername("username");
        appUser.setFirstName("First");
        appUser.setLastName("Last");
        appUser.setEmail("Email");
        appUser.setPasswordHash("test");
        return appUser;
    }

    @Test
    public void updateTour() {
        AppUser user = appUserRepository.save(createUser());
        Tour tour = tourRepository.save(createTour(user));

        TourDto validTourDto = createValidTourCreationDto();
        validTourDto.setCost(30);

        tourRestRequestHandler.updateTour(tour.getId(), validTourDto);

        Optional<Tour> optionalTour = tourRepository.findById(tour.getId());

        Assert.assertTrue(optionalTour.isPresent());
        Assert.assertEquals(30, optionalTour.get().getTourCost(), 0);
    }

    @Test
    public void deleteTour() {
        AppUser user = appUserRepository.save(createUser());
        Tour tour = tourRepository.save(createTour(user));

        Optional<Tour> optionalTour = tourRepository.findById(tour.getId());
        Assert.assertTrue(optionalTour.isPresent());

        tourRestRequestHandler.deleteTour(tour.getId());

        optionalTour = tourRepository.findById(tour.getId());
        Assert.assertFalse(optionalTour.isPresent());
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