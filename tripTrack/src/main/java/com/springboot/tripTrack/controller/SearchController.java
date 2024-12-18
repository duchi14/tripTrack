package com.springboot.tripTrack.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.tripTrack.dto.LocationDto;
import com.springboot.tripTrack.dto.MessageDto;
import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.SearchIngPage;
import com.springboot.tripTrack.dto.TagDto;
import com.springboot.tripTrack.dto.TrackPostDto;
import com.springboot.tripTrack.service.LocationService;
import com.springboot.tripTrack.service.PostService;
import com.springboot.tripTrack.service.TagService;
import com.springboot.tripTrack.service.TrackPostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SearchController {
	private final TrackPostService tpService;
	private final TagService tService;
	private final LocationService lService;
	private final PostService pService;

	@GetMapping(path = "/trackSearch")
	public ModelAndView TrachSearch(@RequestParam("searchValue") String searchValue) throws Exception {
		ModelAndView mav = new ModelAndView("trackSearch");
		if (searchValue == "") {
			int nowPage = 1;
			// 표시할 게시물 숫자
			int limitPage = 10;

			// 표시할 페이지 숫자
			int pageListLimit = 10;

			// DB시작 지점
			int startRow = (nowPage - 1) * limitPage;

			// 시작 페이지
			int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;

			// 끝 페이지
			int endPage = startPage + pageListLimit - 1;
			int listCount = tpService.findAllTrackCount();

			// 전체 리스트 갯수
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			SearchIngPage pDto = new SearchIngPage(searchValue, startPage, endPage, nowPage, maxPage, pageListLimit,
					listCount, limitPage);
			List<TrackPostDto> plist = tpService.findAllTrackPost(startRow, limitPage);
			System.out.println(pDto);

			mav.addObject("trackList", plist);
			mav.addObject("pDto", pDto);
			return mav;
		} else if (searchValue.length() == 1) {
			ModelAndView mav2 = new ModelAndView("Message");
			mav2.addObject("data", new MessageDto("한글자 검색은 불가능합니다.", "/sea"));
			return mav2;
		} else {
			int nowPage = 1;

			// 표시할 게시물 숫자
			int limitPage = 10;

			// 표시할 페이지 숫자
			int pageListLimit = 10;

			// DB시작 지점
			int startRow = (nowPage - 1) * limitPage;

			// 시작 페이지
			int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;
			int listCount = tpService.findTrackListCount(searchValue);
			// 끝 페이지
			int endPage = startPage + pageListLimit - 1;
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			SearchIngPage pDto = new SearchIngPage(searchValue, startPage, endPage, nowPage, maxPage, pageListLimit,
					listCount, limitPage);
			List<TrackPostDto> plist = tpService.findTrackList(searchValue, startRow, limitPage);
			mav.addObject("trackList", plist);
			mav.addObject("pDto", pDto);
			return mav;
		}
	}

	@GetMapping(path = "/t")
	public ModelAndView TSearch(@RequestParam("searchValue") String searchValue, @RequestParam("page") int nowPage)
			throws Exception {
		ModelAndView mav = new ModelAndView("trackSearch");
		if (searchValue == "") {
			// 표시할 게시물 숫자
			int limitPage = 10;

			// 표시할 페이지 숫자
			int pageListLimit = 10;

			// DB시작 지점
			int startRow = (nowPage - 1) * limitPage;

			// 시작 페이지
			int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;

			// 끝 페이지
			int endPage = startPage + pageListLimit - 1;
			int listCount = tpService.findAllTrackCount();

			// 전체 리스트 갯수
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			SearchIngPage pDto = new SearchIngPage(searchValue, startPage, endPage, nowPage, maxPage, pageListLimit,
					listCount, limitPage);
			List<TrackPostDto> plist = tpService.findAllTrackPost(startRow, limitPage);
			mav.addObject("trackList", plist);
			mav.addObject("pDto", pDto);
			return mav;
		} else if (searchValue.length() == 1) {
			ModelAndView mav2 = new ModelAndView("Message");
			mav2.addObject("data", new MessageDto("한글자 검색은 불가능합니다.", "/sea"));
			return mav2;
		} else {

			// 표시할 게시물 숫자
			int limitPage = 5;

			// 표시할 페이지 숫자
			int pageListLimit = 10;

			// DB시작 지점
			int startRow = (nowPage - 1) * limitPage;

			// 시작 페이지
			int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;
			int listCount = tpService.findTrackListCount(searchValue);
			// 끝 페이지
			int endPage = startPage + pageListLimit - 1;
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			SearchIngPage pDto = new SearchIngPage(searchValue, startPage, endPage, nowPage, maxPage, pageListLimit,
					listCount, limitPage);
			List<TrackPostDto> plist = tpService.findTrackList(searchValue, startRow, limitPage);
			mav.addObject("trackList", plist);
			mav.addObject("pDto", pDto);
			return mav;
		}
	}

	@GetMapping(path = "/search")
	public ModelAndView Search(@RequestParam("searchValue") String searchValue) throws Exception {

		List<TagDto> TList = tService.getAllTag();
		List<LocationDto> LList = lService.getAllLoc();
		ModelAndView mav = new ModelAndView("search");
		mav.addObject("Tlist", TList);
		mav.addObject("Llist", LList);
		if (searchValue == "") {
			int nowPage = 1;
			// 표시할 게시물 숫자
			int limitPage = 10;

			// 표시할 페이지 숫자
			int pageListLimit = 10;

			// DB시작 지점
			int startRow = (nowPage - 1) * limitPage;

			// 시작 페이지
			int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;

			// 끝 페이지
			int endPage = startPage + pageListLimit - 1;
			int listCount = pService.findAllCount();

			// 전체 리스트 갯수
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			SearchIngPage pDto = new SearchIngPage(searchValue, startPage, endPage, nowPage, maxPage, pageListLimit,
					listCount, limitPage);
			List<PostDto> plist = pService.findAllPost(startRow, limitPage);
			mav.addObject("plist", plist);
			mav.addObject("pDto", pDto);
			return mav;
		} else if (searchValue.length() == 1) {
			ModelAndView mav2 = new ModelAndView("Message");
			mav2.addObject("data", new MessageDto("한글자 검색은 불가능합니다.", "/sea"));
			return mav2;
		} else {
			int nowPage = 1;

			// 표시할 게시물 숫자
			int limitPage = 10;

			// 표시할 페이지 숫자
			int pageListLimit = 10;

			// DB시작 지점
			int startRow = (nowPage - 1) * limitPage;

			// 시작 페이지
			int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;
			int listCount = pService.findSearchCount(searchValue);
			// 끝 페이지
			int endPage = startPage + pageListLimit - 1;
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			SearchIngPage pDto = new SearchIngPage(searchValue, startPage, endPage, nowPage, maxPage, pageListLimit,
					listCount, limitPage);
			List<PostDto> plist = pService.findList(searchValue, startRow, limitPage);
			mav.addObject("plist", plist);
			mav.addObject("pDto", pDto);
			return mav;
		}

	}

	@GetMapping(path = "/s")
	public ModelAndView SearchPage(@RequestParam("searchValue") String searchValue, @RequestParam("page") int nowPage)
			throws Exception {
		ModelAndView mav = new ModelAndView("search");
		if (searchValue == "") {
			// 표시할 게시물 숫자
			int limitPage = 10;

			// 표시할 페이지 숫자
			int pageListLimit = 10;

			// DB시작 지점
			int startRow = (nowPage - 1) * limitPage;

			// 시작 페이지
			int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;
			int listCount = pService.findAllCount();
			// 끝 페이지
			int endPage = startPage + pageListLimit - 1;
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			List<TagDto> TList = tService.getAllTag();
			List<LocationDto> LList = lService.getAllLoc();
			SearchIngPage pDto = new SearchIngPage(searchValue, startPage, endPage, nowPage, maxPage, pageListLimit,
					listCount, limitPage);

			List<PostDto> plist = pService.findAllPost(startRow, limitPage);
			mav.addObject("Tlist", TList);
			mav.addObject("Llist", LList);
			mav.addObject("plist", plist);
			mav.addObject("pDto", pDto);
			return mav;
		} else {

			// 표시할 게시물 숫자
			int limitPage = 10;

			// 표시할 페이지 숫자
			int pageListLimit = 10;

			// DB시작 지점
			int startRow = (nowPage - 1) * limitPage;

			// 시작 페이지
			int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;
			int listCount = pService.findSearchCount(searchValue);
			// 끝 페이지
			int endPage = startPage + pageListLimit - 1;
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			SearchIngPage pDto = new SearchIngPage(searchValue, startPage, endPage, nowPage, maxPage, pageListLimit,
					listCount, limitPage);
			List<PostDto> plist = pService.findList(searchValue, startRow, limitPage);
			mav.addObject("plist", plist);
			mav.addObject("pDto", pDto);
			return mav;
		}
	}

}
