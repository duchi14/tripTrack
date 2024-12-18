package com.springboot.tripTrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.tripTrack.dto.BookmarkDto;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.service.BookmarkService;
import com.springboot.tripTrack.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BookmarkController {
	private final BookmarkService bmService;
	
    @GetMapping("/bookmark.status")
    @ResponseBody
    public boolean getBookmarkStatus(@RequestParam("loginedUserId") String user_id, @RequestParam("bookMarkedPostId") Integer post_id) {
        return bmService.isBookmarked(user_id, post_id);
    }
	
    @GetMapping("/bookmark.toggle")
    @ResponseBody
    public String toggleBookmark(
    		@RequestParam("loginedUserId")String user_id, 
			@RequestParam("bookMarkedPostId") Integer post_id,
			@RequestParam("lat") String lat,
			@RequestParam("lng") String lng,
			@RequestParam("placeName") String placeName) {
    	BookmarkDto dto = new BookmarkDto();
    	boolean bookmarked = getBookmarkStatus(user_id, post_id); //true면 이미 북마크 되어있는 것.
    	String message = "";
    	if(user_id != "notLogined") {

	    	
	    	if(bookmarked) {
	    		message = deleteBookmarkByPostId(user_id, post_id);
	    	} else {
	    		if(lat.isEmpty() && lng.isEmpty() && placeName.isEmpty()) {
	    			LatLngDto gps = bmService.selectGpsByPostId(post_id);
	    			dto.setLat(gps.getLat());
	    			dto.setLng(gps.getLng());
	    			dto.setPlaceName(gps.getPlaceName());
		    	} else {
		    		dto.setLat(lat);
	    			dto.setLng(lng);
	    			dto.setPlaceName(placeName);
		    	}
	    			dto.setUser_id(user_id);
	    			dto.setPost_id(post_id);
	    			
	    			if(bmService.createBookmark(dto)) {
	    				message = "이 글을 북마크하셨습니다!";
	    			} else {
	    				message = "실패!";
	    			}
	    		} 
    	}
    	
    	return message;
    }
    
	@GetMapping("/delBookmark.do")
	@ResponseBody
	public String deleteBookmarkByPostId(@RequestParam("loginedUserId")String user_id, @RequestParam("bookMarkedPostId")int post_id) {
		String message;
		if(bmService.deleteBookmark(user_id, post_id)) {
			message = "북마크가 삭제되었습니다!";
		} else {
			message = "북마크 삭제에 실패하였습니다.";
		}
		return message;
	}
	
}
