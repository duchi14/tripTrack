package com.springboot.tripTrack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.springboot.tripTrack.dto.LocationDto;

@Mapper
public interface LocationDao {
	@Select("select * from tbl_location")
	List<LocationDto> selectAllLoc();

}
