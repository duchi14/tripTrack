package com.springboot.tripTrack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.TrackPostDto;

@Mapper
public interface TrackPostDao {
	@Select("select trackPost_id from tbl_trackPost order by trackPost_id desc limit 1")
	public Integer getLastPostNum() throws DataAccessException;
	
	@Insert("insert into tbl_trackPost values (#{user_id}, #{board_id}, #{trackPost_id}, #{post_id}, #{lat}, #{lng}, #{placeName}, #{title}, #{content})")
	boolean insertTrackPost(TrackPostDto tpDto) throws DataAccessException;
	
	@Select("select * from tbl_trackPost limit #{startRow}, #{limitPage}")
	public List<TrackPostDto> selectAllTrackList(@Param("startRow") int startRow, @Param("limitPage") int limitPage) throws DataAccessException;
	
	@Select("select * from tbl_trackPost where trackPost_id = #{id}")
	public TrackPostDto selectTrackPostByPostId(int id) throws DataAccessException;
	
	@Select("select count(trackPost_id) as count from tbl_trackpost")
	public int findAllTrackCount() throws DataAccessException;
 
	@Select("select * from tbl_trackpost limit #{startRow}, #{limitPage}")
	public List<TrackPostDto> findAllTrackPost(@Param("startRow") int startRow, @Param("limitPage") int limitPage) throws DataAccessException; 

	@Select("SELECT count(trackPost_id) as count FROM tbl_trackpost WHERE MATCH (placeName,title) AGAINST (#{searchValue} in boolean mode)")
	public int findTrackListCount(@Param("searchValue") String searchValue) throws DataAccessException;

	@Select("SELECT * FROM tbl_trackpost WHERE MATCH (placeName,title) AGAINST (#{searchValue} in boolean mode) limit #{startRow}, #{limitPage}")
	public List<TrackPostDto> findTrackList(@Param("searchValue") String searchValue, @Param("startRow") int startRow, @Param("limitPage") int limitPage) throws DataAccessException;

	@Select("select * from tbl_trackPost")
	public List<TrackPostDto> selectAllTrack() throws DataAccessException;

	@Select("select * from tbl_trackPost where user_id = #{user_id}")
	public List<TrackPostDto> selectTrackListByUserId(String user_id) throws DataAccessException;
	
	//마이페이지 페이징 테스트
	 @Select(" SELECT * FROM tbl_trackpost WHERE user_id = #{userId} LIMIT #{limit} OFFSET #{offset}")
	List<TrackPostDto> findPostsByUserId(@Param("userId") String userId, @Param("limit") int limit, @Param("offset") int offset) throws DataAccessException;

	 @Select("SELECT COUNT(*) FROM tbl_trackpost WHERE user_id = #{userId}")
	 int countPostsByUserId(@Param("userId") String userId) throws DataAccessException;
//////////////////////////////////////////////////////////////////////////////////////////////////////////
	 @Select("select * from tbl_trackpost order by trackPost_id desc limit #{startRow}, #{limitPage}")
	public List<TrackPostDto> selectPaigingTrackList(@Param("startRow")int startRow, @Param("limitPage")int limitPage) throws DataAccessException;
}