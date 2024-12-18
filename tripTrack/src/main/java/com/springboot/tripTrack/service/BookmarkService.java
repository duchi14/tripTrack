package com.springboot.tripTrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dto.BookmarkDto;
import com.springboot.tripTrack.dto.LatLngDto;

@Service
public interface BookmarkService {
	List<BookmarkDto> selectAllBookmarkByUserId(String user_id);
	boolean createBookmark(BookmarkDto dto);
	boolean deleteBookmark(String user_id, int post_id);
	boolean isBookmarked(String user_id, Integer post_id);
	LatLngDto selectGpsByPostId(Integer post_id);
}
