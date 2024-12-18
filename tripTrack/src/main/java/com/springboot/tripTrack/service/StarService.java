package com.springboot.tripTrack.service;

import com.springboot.tripTrack.dto.ReviewDto;

public interface StarService {
	void insertStar(ReviewDto Dto);
	double avg_star(int post_id);
	int cnt_star(int post_id);
	boolean deleteStar(int review_id);
}
