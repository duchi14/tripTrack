package com.springboot.tripTrack.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.tripTrack.dto.ReviewDto;
import com.springboot.tripTrack.service.ReviewService;
import com.springboot.tripTrack.service.StarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ReviewController {

	private final ReviewService rService;
	private final StarService sService;

	@PostMapping(path = "/review.do")
	public String insertBoard(ReviewDto dto, Authentication authentication) throws Exception {

		UserDetails user_id = (UserDetails) authentication.getPrincipal();

		int review_id = rService.findreivewNum();
		int post_id = dto.getPost_id();
		dto.setUser_id(user_id.getUsername());
		dto.setReview_id(review_id);
		rService.insertReview(dto);
		sService.insertStar(dto);

		return "redirect:/Viewarticles?post_id=" + post_id;
	}

	@PostMapping(path = "/delReview")
	public String deleteReview(@RequestParam("review_id") int review_id, @RequestParam("post_id") Integer post_id)
			throws Exception {
		rService.deleteReview(review_id);
		sService.deleteStar(review_id);
		return "redirect:/Viewarticles?post_id=" + post_id;
	}
}
