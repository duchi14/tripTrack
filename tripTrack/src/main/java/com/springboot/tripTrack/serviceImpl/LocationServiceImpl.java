package com.springboot.tripTrack.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dao.LocationDao;
import com.springboot.tripTrack.dto.LocationDto;
import com.springboot.tripTrack.service.LocationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

	private final LocationDao locDao;
	
	public List<LocationDto> getAllLoc() {
		List<LocationDto> locList = locDao.selectAllLoc();
		
		return locList;
	}

}
