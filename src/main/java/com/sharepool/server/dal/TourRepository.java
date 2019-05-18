package com.sharepool.server.dal;

import org.springframework.data.repository.CrudRepository;

import com.sharepool.server.domain.Tour;

public interface TourRepository extends CrudRepository<Tour, Long> {
}
