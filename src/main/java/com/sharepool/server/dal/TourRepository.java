package com.sharepool.server.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sharepool.server.domain.AppUser;
import com.sharepool.server.domain.Tour;

public interface TourRepository extends CrudRepository<Tour, Long> {
	List<Tour> findAllByOwner(AppUser owner);
}
