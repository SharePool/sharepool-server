package com.sharepool.server.logic.tour;

import com.sharepool.server.common.AbstractUtilTest;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.util.auth.UserContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TourRestRequestHandlerTest extends AbstractUtilTest {

    @Autowired
    private TourRestRequestHandler tourRestRequestHandler;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllToursForUser() {
        User user = userRepository.save(createValidUser());

        TourDto validTourDto = createValidTourDto();
        validTourDto.setOwnerId(user.getId());

        UserContext userContext = new UserContext();
        userContext.setUser(user);
        tourRestRequestHandler.createTour(validTourDto, userContext);

        List<TourDto> allToursForUser = tourRestRequestHandler.getAllToursForUser(userContext, false);

        Assert.assertEquals(1, allToursForUser.size());
        Assert.assertEquals(validTourDto.getFrom(), allToursForUser.get(0).getFrom());
    }

    @Test
    public void updateTour() {
        User user = userRepository.save(createValidUser());
        Tour tour = tourRepository.save(createValidTour(user));

        TourDto validTourDto = createValidTourDto();
        validTourDto.setCost(30);

        UserContext userContext = new UserContext();
        userContext.setUser(user);
        tourRestRequestHandler.updateTour(tour.getId(), validTourDto, userContext);

        Optional<Tour> optionalTour = tourRepository.findById(tour.getId());

        Assert.assertTrue(optionalTour.isPresent());
        Assert.assertEquals(30, optionalTour.get().getTourCost(), 0);
    }

    @Test
    public void deleteTour() {
        User user = userRepository.save(createValidUser());
        Tour tour = tourRepository.save(createValidTour(user));

        Optional<Tour> optionalTour = tourRepository.findById(tour.getId());
        Assert.assertTrue(optionalTour.isPresent());

        UserContext userContext = new UserContext();
        userContext.setUser(user);
        tourRestRequestHandler.deleteTour(tour.getId(), userContext);

        optionalTour = tourRepository.findById(tour.getId());
        Assert.assertTrue(optionalTour.isPresent());
        Assert.assertFalse(optionalTour.get().isActive());
    }

    @Test
    public void testDeleteAndUpdateOnlyOnMyTours() {
        User user1 = createValidUser();
        User user2 = createValidUser();

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        Tour tour = tourRepository.save(createValidTour(user2));

        Optional<Tour> optionalTour = tourRepository.findById(tour.getId());
        Assert.assertTrue(optionalTour.isPresent());

        UserContext userContext = new UserContext();
        userContext.setUser(user1);

        assertThrows(ResponseStatusException.class,
                () -> tourRestRequestHandler.deleteTour(tour.getId(), userContext));
    }

    @Test
    public void testGetAllToursForUserActiveFilter() {
        User user = createValidUser();

        user = userRepository.save(user);

        Tour activeTour = createValidTour(user);
        Tour inactiveTour = createValidTour(user);

        activeTour.setActive(true);
        inactiveTour.setActive(false);

        tourRepository.saveAll(Arrays.asList(activeTour, inactiveTour));

        UserContext userContext = new UserContext();
        userContext.setUser(user);

        List<TourDto> allToursForUser = tourRestRequestHandler.getAllToursForUser(userContext, false);
        Assert.assertEquals(1, allToursForUser.size());

        allToursForUser = tourRestRequestHandler.getAllToursForUser(userContext, true);
        Assert.assertEquals(2, allToursForUser.size());
    }
}