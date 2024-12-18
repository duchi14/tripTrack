package com.springboot.tripTrack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.tripTrack.dto.BookmarkDto;
import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.TrackPostDto;
import com.springboot.tripTrack.fileService.FileService;
import com.springboot.tripTrack.memberDto.MemberDto;
import com.springboot.tripTrack.service.BookmarkService;
import com.springboot.tripTrack.service.MemberService;
import com.springboot.tripTrack.service.PostService;
import com.springboot.tripTrack.service.TrackPostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MyPageController {
	private final MemberService memberService;
	private final FileService fService;
	private final BookmarkService bookmarkService;
	private final TrackPostService tpService;
	private final PostService postService;

	@GetMapping(path = "/page.do")
	public ModelAndView viewMyPage(@RequestParam("user_id") String user_id) throws Exception {
		List<BookmarkDto> bookmarklist = bookmarkService.selectAllBookmarkByUserId(user_id);
		List<TrackPostDto> trackList = tpService.selectTrackListByUserId(user_id);
		List<PostDto> postList = postService.selectPostListByUserId(user_id);
		MemberDto member = memberService.getMemberByUserId(user_id);
		String fileName = fService.selectProFile(user_id);

		ModelAndView mav = new ModelAndView("myPage");

		mav.addObject("postList", postList);
		mav.addObject("fileName", fileName);
		mav.addObject("member", member);
		mav.addObject("user_id", user_id);
		mav.addObject("bookmarklist", bookmarklist);
		mav.addObject("trackList", trackList);

		return mav;
	}

	// 페이징
	@GetMapping("/posts")
	public ResponseEntity<Map<String, Object>> getPosts(@RequestParam("user_id") String userId,
			@RequestParam("page") int page, @RequestParam("size") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<PostDto> postsPage = postService.findPostsByUserId(userId, pageRequest);

		Map<String, Object> response = new HashMap<>();
		response.put("posts", postsPage.getContent());
		response.put("totalPages", postsPage.getTotalPages());
		response.put("currentPage", postsPage.getNumber());

		return ResponseEntity.ok(response);
	}

	// 페이징
	@GetMapping("/trackPosts")
	public ResponseEntity<Map<String, Object>> getTrackPosts(@RequestParam("user_id") String userId,
			@RequestParam("page") int page, @RequestParam("size") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<TrackPostDto> postsPage = tpService.findTrackPostsByUserId(userId, pageRequest);

		Map<String, Object> response = new HashMap<>();
		response.put("trackPosts", postsPage.getContent());
		response.put("totalPages", postsPage.getTotalPages());
		response.put("currentPage", postsPage.getNumber());

		return ResponseEntity.ok(response);
	}
}