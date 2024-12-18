package com.springboot.tripTrack.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.springboot.tripTrack.FileDto.FileDto;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.TrackPostDto;

public interface TrackPostService {

	boolean insertTrackPost(TrackPostDto tpDto);


	TrackPostDto getTrackPostByPostId(int id);

	List<LatLngDto> getGPSList(TrackPostDto tpDto);
	
	int findAllTrackCount();

	List<TrackPostDto> getAllTrackList(int startRow, int limitPage);

	List<TrackPostDto> findAllTrackPost(int startRow, int limitPage);
	
	int findTrackListCount(String searchValue);
	
	
	List<TrackPostDto> findTrackList(String searchValue, int startRow, int limitPage);


	List<TrackPostDto> getAllTrackList();


	List<TrackPostDto> selectTrackListByUserId(String user_id);


	List<FileDto> getFileListByTrackPostId(TrackPostDto tpDto);
	
	//페이징
	Page<TrackPostDto> findTrackPostsByUserId(String userId, PageRequest pageRequest);


	List<TrackPostDto> getPagingTrackList(int startRow, int limitPage);
}
