package com.springboot.tripTrack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import com.springboot.tripTrack.dto.ReviewDto;

@Mapper
public interface ReviewDao {
	@Select("select s.star_point,r.review_id,r.post_id,r.user_id,r.content,r.title, r.localDate from tbl_review as r \r\n"
			+ "join tbl_star as s \r\n"
			+ "on r.review_id = s.review_id \r\n"
			+ "where r.post_id = ${post_id} \r\n"
			+ "group by r.review_id,s.star_point,r.user_id")
	public List<ReviewDto> findAllreview(int post_id) throws DataAccessException;
	
	@Select("select review_id from tbl_review order by review_id desc limit 1")
	public String LastRevivewNum() throws DataAccessException;
	
	
	@Insert("insert into tbl_review values(#{review_id}, #{post_id}, #{title}, #{content}, #{hit_cnt}, #{user_id}, default, default)")
	public void insertReview(ReviewDto board) throws DataAccessException;
	
	@Delete("delete from tbl_review where review_id = #{review_id}")
	public boolean deleteReview(int review_id) throws DataAccessException;
}
