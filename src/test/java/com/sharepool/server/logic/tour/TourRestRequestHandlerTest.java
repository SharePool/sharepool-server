package com.sharepool.server.logic.tour;

import com.sharepool.server.common.AbstractUtilTest;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.tour.dto.TourDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        tourRestRequestHandler.createTour(validTourDto);

        List<TourDto> allToursForUser = tourRestRequestHandler.getAllToursForUser(user.getId());

        Assert.assertEquals(1, allToursForUser.size());
        Assert.assertEquals(validTourDto.getFrom(), allToursForUser.get(0).getFrom());
    }



    @Test
    public void updateTour() {
        User user = userRepository.save(createValidUser());
        Tour tour = tourRepository.save(createValidTour(user));

        TourDto validTourDto = createValidTourDto();
        validTourDto.setCost(30);

        tourRestRequestHandler.updateTour(tour.getId(), validTourDto);

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

        tourRestRequestHandler.deleteTour(tour.getId());

        optionalTour = tourRepository.findById(tour.getId());
        Assert.assertFalse(optionalTour.isPresent());
    }
}