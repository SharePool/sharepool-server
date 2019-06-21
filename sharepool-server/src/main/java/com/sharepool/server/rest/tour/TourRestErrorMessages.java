package com.sharepool.server.rest.tour;

public class TourRestErrorMessages {

	private TourRestErrorMessages() {
	}

	public static String noUserFound(Long userId) {
		return String.format("No User with id %d found", userId);
	}

	public static String noTourFound(Long tourId) {
		return String.format("No Tour with id %d found", tourId);
	}
}
