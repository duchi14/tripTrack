package com.springboot.tripTrack.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dao.PostDao;
import com.springboot.tripTrack.dao.ReviewDao;
import com.springboot.tripTrack.dto.ReviewDto;
import com.springboot.tripTrack.fileService.FileService;
import com.springboot.tripTrack.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewDao rDao;
	private final FileService fService;

	public List<ReviewDto> findAllreview(int post_id) {
		List<ReviewDto> list = rDao.findAllreview(post_id);
		List<ReviewDto> list2 = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ReviewDto dto = new ReviewDto();
			dto = list.get(i);
			dto.setLocalDate(localDateSplit(dto));
			dto.setStar_point((Math.round(dto.getStar_point() * 10) / 10) / 2.0);
			dto.setProFile(fService.selectProFile(dto.getUser_id()));
			
			list2.add(dto);
		}
		return list2;
	}

	public void insertReview(ReviewDto board) {
		board.setHit_cnt(0);
		System.out.println(board.getReview_id());
		rDao.insertReview(board);
	}

	public int findreivewNum() {
		int review_id;
		if (rDao.LastRevivewNum() == null) {
			review_id = 1;
		} else {
			review_id = Integer.parseInt(rDao.LastRevivewNum()) + 1;
		}
		return review_id;
	}

	//날짜에서 시간 자르기
	private String localDateSplit(ReviewDto dto) {
		String localDateOrigin = dto.getLocalDate();
		String localDateCuttingTime = localDateOrigin.substring(0, 10);
		return localDateCuttingTime;
	}
	
	//리뷰 삭제
	public boolean deleteReview(int review_id) {
		return rDao.deleteReview(review_id);
	}
}
