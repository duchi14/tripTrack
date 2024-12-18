package com.springboot.tripTrack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import com.springboot.tripTrack.dto.BookmarkDto;
import com.springboot.tripTrack.dto.LatLngDto;

@Mapper
public interface BookmarkDao {
	@Insert("insert into tbl_bookmark values (#{user_id}, #{post_id}, #{lat}, #{lng}, #{placeName})")
	public void insertBookmark(BookmarkDto dto)throws DataAccessException;
	
	@Select("select count(post_id) as cntBookmark from tbl_bookmark where user_id = #{user_id}")
	public Integer cntBookmarkByUserId(String user_id)throws DataAccessException;
	
	@Select("select p.category_name, p.tag_name, p.city_name, b.*\r\n"
			+ "from tbl_post as p\r\n"
			+ "left join tbl_bookmark as b\r\n"
			+ "on p.post_id = b.post_id\r\n"
			+ "where b.user_id = #{user_id}")
	public List<BookmarkDto> selectAllBmByUserId(String user_id) throws DataAccessException;
	
	@Delete("delete from tbl_bookmark where user_id = #{user_id} and post_id = #{post_id}")
	public boolean deleteBookmark(@Param("user_id")String user_id, @Param("post_id")int post_id) throws DataAccessException;

	@Select("select post_id from tbl_bookmark where user_id = #{user_id}")
	public List<Integer> selectBookmarkedPostIdByUserId(String user_id) throws DataAccessException;

	@Select("select * from tbl_gps where post_id = #{post_id}")
	public LatLngDto selectGpsByPostId(Integer post_id) throws DataAccessException;

}
