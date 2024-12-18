package com.springboot.tripTrack.serviceImpl;

import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dao.StarDao;
import com.springboot.tripTrack.dto.ReviewDto;
import com.springboot.tripTrack.service.StarService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class StarServiceImpl implements StarService {
	private final StarDao sDao;

	@Override
	public void insertStar(ReviewDto Dto) {
		 System.out.println(Dto.getStar_point());
		 sDao.insertStar(Dto);
	 }
	
	 public double avg_star(int post_id) {
		 Integer avg_star =  sDao.avg_star(post_id);
		 
		 if(avg_star == null) {
			 return 0;
		 }
		
		 return ((Math.round((double)avg_star)*10/2)/10.0);
	 }
	 
	 public int cnt_star(int post_id) {
		 int cnt_star;
		 
		 if(sDao.cnt_star(post_id) != null) {
			 cnt_star = Integer.parseInt(sDao.cnt_star(post_id));
		 }else {
			 cnt_star = 0;
		 }
		 
		 return cnt_star;
	 }
	 
	 public boolean deleteStar(int review_id) {
		 return sDao.deleteStar(review_id);
	 }
}
