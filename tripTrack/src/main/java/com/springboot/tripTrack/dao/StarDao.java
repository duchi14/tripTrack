package com.springboot.tripTrack.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import com.springboot.tripTrack.dto.ReviewDto;

@Mapper
public interface StarDao {
	@Insert("insert into tbl_star values(#{post_id},#{review_id},#{user_id}, #{star_point})")
	public void insertStar(ReviewDto Dto) throws DataAccessException;
	
	@Select("select avg(star_point) as avg_star from tbl_star where post_id = #{post_id}")
	public Integer avg_star(int post_id) throws DataAccessException;
	
	@Select("select count(star_point) as cnt_star from tbl_star where post_id = #{post_id}")
	public String cnt_star(int post_id) throws DataAccessException;

	@Delete("delete from tbl_star where review_id = #{review_id}")
	public boolean deleteStar(int review_id) throws DataAccessException;
}
