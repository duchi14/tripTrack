package com.springboot.tripTrack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.springboot.tripTrack.dto.TagDto;

@Mapper
public interface TagDao {
	
	@Select("select * from tbl_tag")
	List<TagDto> selectAllTag();

}
