package com.springboot.tripTrack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import com.springboot.tripTrack.dto.ArticleDto;
import com.springboot.tripTrack.dto.BookmarkDto;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.ReadArticleDto;

@Mapper
public interface PostDao {

	@Select("select post_id from tbl_post order by post_id desc limit 1")
	public String LastNum() throws DataAccessException;

	@Select("select p.post_id, p.title, p.content, p.city_name, p.tag_name, g.lat, g.lng, g.placeName from tbl_post as p\r\n"
			+ "join tbl_gps as g\r\n" + "on p.post_id = g.post_id\r\n" + "where p.post_id = #{post_id}")
	public ReadArticleDto findById(int post_id) throws DataAccessException;

	@Insert("insert into tbl_post(post_id, board_id, category_name, city_name,tag_name, user_id, title,content) values(#{post_id}, 1, #{category_name}, #{city_name},#{tag_name},#{user_id},#{title}, #{content})")
	public void insertPost(ArticleDto ADto) throws DataAccessException;

	@Insert("insert into tbl_gps(post_id, lat,lng,placeName) values(#{post_id},#{lat}, #{lng},#{placeName})")
	public void insertLatlng(LatLngDto LatDto) throws DataAccessException;

	@Select("SELECT p.post_id, p.title, p.tag_name, p.category_name, p.city_name, avg(s.star_point) AS avg_star, count(s.star_point) as cnt_star \r\n"
			+ "FROM tbl_post AS p \r\n" + "LEFT JOIN tbl_star AS s \r\n" 
			+ "ON p.post_id = s.post_id \r\n"
			+ "GROUP BY p.post_id, p.category_name, p.title, p.city_name, p.tag_name \r\n" 
			+"ORDER BY avg_star desc \r\n"
			+ "limit #{startRow}, #{limitPage}")
	public List<PostDto> findAllPost(@Param("startRow") int startRow, @Param("limitPage") int limitPage) throws DataAccessException;

	@Select("SELECT * FROM tbl_post WHERE MATCH (city_name, tag_name, title, content) AGAINST (#{searchValue} in boolean mode) limit #{startRow}, #{limitPage}")
	public List<PostDto> findList(@Param("searchValue") String searchValue, @Param("startRow") int startRow, @Param("limitPage") int limitPage) throws DataAccessException;
	
	@Select("select lat, lng, placeName from tbl_gps where post_id = #{post_id}")
	public BookmarkDto selectGPS(int post_id) throws DataAccessException;
	
	@Select("SELECT count(post_id) as count FROM tbl_post WHERE MATCH (city_name, tag_name, title, content) AGAINST (#{searchValue} in boolean mode)")
	public int findSearchCount(String searchValue) throws DataAccessException;
	
	@Select("select count(post_id) as count from tbl_post")
	public int findAllCount() throws DataAccessException;
	
	@Select("select * from tbl_post where user_id = #{user_id}")
	public List<PostDto> selectPostListByUserId(String user_id) throws DataAccessException;
	
	//마이페이지 페이징 테스트
	 @Select(" SELECT * FROM tbl_post WHERE user_id = #{userId} LIMIT #{limit} OFFSET #{offset}")
	List<PostDto> findPostsByUserId(@Param("userId") String userId, @Param("limit") int limit, @Param("offset") int offset) throws DataAccessException;

    @Select("SELECT COUNT(*) FROM tbl_post WHERE user_id = #{userId}")
    int countPostsByUserId(@Param("userId") String userId) throws DataAccessException;
}
