package com.sharepool.server.dal;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TourRepository extends CrudRepository<Tour, Long> {
	List<Tour> findAllByOwner(User owner);

	Optional<Tour> findByIdAndOwner(Long tourId, User owner);
}
