package com.springboot.tripTrack.service;

import java.util.List;

import com.springboot.tripTrack.dto.ReviewDto;

public interface ReviewService {
	List<ReviewDto> findAllreview(int post_id);
	void insertReview(ReviewDto board);
	int findreivewNum();
	boolean deleteReview(int review_id);
}
