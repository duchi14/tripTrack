package com.springboot.tripTrack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import com.springboot.tripTrack.dto.BestPostDto;
import com.springboot.tripTrack.dto.CategoryDto;
import com.springboot.tripTrack.dto.CitySearchDto;
import com.springboot.tripTrack.dto.LocationDto;
import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.TagDto;
import com.springboot.tripTrack.dto.TagSearchDto;
import com.springboot.tripTrack.dto.TotalTagSearchDto;

@Mapper
public interface MainDao {
	@Select("select * from tbl_category order by category_id")
	public List<CategoryDto> selectAllCategory() throws DataAccessException;

	@Select("select * from tbl_tag where category_name=#{'cat_name'}")
	public List<TagDto> selectTagByCatName(String cat_name) throws DataAccessException;

	@Select("select * from tbl_location")
	public List<LocationDto> selectAllLocation() throws DataAccessException;


	@Select("SELECT p.post_id, p.title, p.tag_name, p.city_name, avg(s.star_point) AS avg_star, count(s.star_point) as cnt_star\r\n"
			+ "FROM tbl_post AS p \r\n"
			+ "LEFT JOIN tbl_star AS s \r\n"
			+ "ON p.post_id = s.post_id \r\n"
			+ "WHERE p.category_name = #{cat_name} \r\n"
			+ "GROUP BY p.post_id, p.category_name, p.title, p.city_name, p.tag_name\r\n"
			+ "ORDER BY avg_star desc\r\n"
			+ "limit #{startRow}, #{limitPage}")
	public List<PostDto> selectPostByCatName(@Param("cat_name") String cat_name, @Param("startRow") int startRow, @Param("limitPage") int limitPage) throws DataAccessException;
	
	@Select("SELECT p.post_id, p.category_name, p.title, p.tag_name, avg(s.star_point) AS avg_star \r\n"
			+ "FROM tbl_post AS p \r\n"
			+ "LEFT JOIN tbl_star AS s \r\n"
			+ "ON p.post_id = s.post_id \r\n"
			+ "WHERE p.category_name = #{bp_cat}\r\n"
			+ "GROUP BY p.post_id, p.category_name, p.title, p.tag_name \r\n"
			+ "ORDER BY avg_star desc  \r\n"
			+ "LIMIT 9")
	public List<BestPostDto> selectBestPostByCatName(String bp_cat) throws DataAccessException;

	@Select("Select count(post_id) as count from tbl_post where category_name = #{cat_name}")
	public int totalCount(String cat_name) throws DataAccessException;
	
	@Select("select * from tbl_post where tag_name in (#{tag_name0},#{tag_name1},#{tag_name2},#{tag_name3},#{tag_name4},#{tag_name5},#{tag_name6},#{tag_name7},#{tag_name8},#{tag_name9}) and category_name = #{cat_name}"
			+ "limit #{startRow}, #{limitPage}")
	public List<PostDto> tagSearch(TagSearchDto dto) throws DataAccessException;
	
	@Select("select count(post_id) as count from tbl_post where tag_name in (#{tag_name0},#{tag_name1},#{tag_name2},#{tag_name3},#{tag_name4},#{tag_name5},#{tag_name6},#{tag_name7},#{tag_name8},#{tag_name9}) and category_name = #{cat_name}")
	public int tagSearchCnt(TagSearchDto dto) throws DataAccessException;
	
	@Select("select * from tbl_post where city_name in(#{city0},#{city1},#{city2},#{city3},#{city4},#{city5},#{city6},#{city7},#{city8}) and category_name = #{cat_name}"
			+ "limit #{startRow}, #{limitPage}")
	public List<PostDto> citySearch(CitySearchDto CDto) throws DataAccessException;
	
	@Select("select count(post_id) as count from tbl_post where city_name in(#{city0},#{city1},#{city2},#{city3},#{city4},#{city5},#{city6},#{city7},#{city8}) and category_name = #{cat_name}")
	public int citySearchCnt(CitySearchDto CDto) throws DataAccessException;
	
	@Select("select count(post_id) as count from tbl_post where category_name = #{cat_name}")
	public int AllListCnt(String cat_name) throws DataAccessException;
	
	@Select("select * from tbl_post where category_name = #{cat_name} limit #{startRow}, #{limitPage}")
	public List<PostDto> AllList(@Param("cat_name") String cat_name, @Param("startRow") int startRow, @Param("limitPage") int limitPage) throws DataAccessException;
	
	@Select("select * from tbl_post where city_name in(#{city0},#{city1},#{city2},#{city3},#{city4},"
			+ "#{city5},#{city6},#{city7},#{city8})"
			+ "and tag_name in (#{tag_name0},#{tag_name1},#{tag_name2},#{tag_name3},"
			+ "#{tag_name4},#{tag_name5},#{tag_name6},#{tag_name7},#{tag_name8},#{tag_name9}) and category_name = #{cat_name}"
			+ "limit #{startRow}, #{limitPage}")
	public List<PostDto> TotalList(TotalTagSearchDto totDto) throws DataAccessException;
	
	@Select("select count(post_id) as count from tbl_post where city_name in(#{city0},#{city1},#{city2},#{city3},#{city4},"
			+ "#{city5},#{city6},#{city7},#{city8})"
			+ "and tag_name in (#{tag_name0},#{tag_name1},#{tag_name2},#{tag_name3},"
			+ "#{tag_name4},#{tag_name5},#{tag_name6},#{tag_name7},#{tag_name8},#{tag_name9}) and category_name = #{cat_name}")
	public int AllCnt(TotalTagSearchDto dto) throws DataAccessException;
}
