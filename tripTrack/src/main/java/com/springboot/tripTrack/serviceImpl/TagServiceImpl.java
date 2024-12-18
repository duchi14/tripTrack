package com.springboot.tripTrack.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dao.PostDao;
import com.springboot.tripTrack.dao.TagDao;
import com.springboot.tripTrack.dto.TagDto;
import com.springboot.tripTrack.service.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

	private final TagDao tagdao;
	
	public List<TagDto> getAllTag() {
		List<TagDto> tagList = tagdao.selectAllTag();
		return  tagList;
	}
	
}
