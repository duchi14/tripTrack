package com.springboot.tripTrack.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.tripTrack.dto.BestPostDto;
import com.springboot.tripTrack.dto.CategoryDto;
import com.springboot.tripTrack.dto.CitySearchDto;
import com.springboot.tripTrack.dto.LocationDto;
import com.springboot.tripTrack.dto.PageDto;
import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.TagDto;
import com.springboot.tripTrack.dto.TagSearchDto;
import com.springboot.tripTrack.dto.TotalTagSearchDto;
import com.springboot.tripTrack.dto.TrackPostDto;
import com.springboot.tripTrack.fileService.FileService;
import com.springboot.tripTrack.service.MainService;
import com.springboot.tripTrack.service.StarService;
import com.springboot.tripTrack.service.TrackPostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	private final MainService mainService;
	private final FileService fService;
	private final StarService sService;
	private final TrackPostService tpService;

	@GetMapping(path = "/") // 메인페이지 이동
	public ModelAndView home(Authentication authentication, HttpSession session) {
		List<CategoryDto> catList = mainService.getAllCategory();
		List<TrackPostDto> trackList = tpService.getAllTrackList();
		List<BestPostDto> bestPlaceList = mainService.getBestPlacePost();
		List<BestPostDto> bestAccList = mainService.getBestAccPost();
		List<BestPostDto> bestRestList = mainService.getRestPost();
		
		if(authentication != null) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String profile = fService.selectProFile(userDetails.getUsername());
			session.setAttribute("profile", profile);
		}

		ModelAndView mav = new ModelAndView("index");
		
		mav.addObject("trackList", trackList);
		mav.addObject("catList", catList);
		mav.addObject("bestPlaceList", bestPlaceList);
		mav.addObject("bestAccList", bestAccList);
		mav.addObject("bestRestList", bestRestList);

		return mav;
	}

	@GetMapping(value = "{cat_name}")
	public ModelAndView getPostList(@PathVariable("cat_name") String cat_name, @RequestParam("page") int nowPage) {
		// 표시할 게시물 숫자
		int limitPage = 10;

		// 표시할 페이지 숫자
		int pageListLimit = 10;

		// DB시작 지점
		int startRow = (nowPage - 1) * limitPage;

		// 전체 리스트 갯수
		int listCount = mainService.getTotalCount(cat_name);

		// 시작 페이지
		int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;

		// 끝 페이지
		int endPage = startPage + pageListLimit - 1;

		int maxPage = (int) Math.ceil((double) listCount / limitPage);
		if (endPage > maxPage) {
			endPage = maxPage;
		}

		PageDto pDto = new PageDto(cat_name, startPage, endPage, nowPage, maxPage, pageListLimit, listCount, limitPage);
		
		List<CategoryDto> catList = mainService.getAllCategory();
		List<LocationDto> locationList = mainService.getAllLocation();
		List<TagDto> tagList = mainService.getTagByCatName(cat_name);
		List<PostDto> postList = mainService.getPostByCatName(cat_name, startRow, limitPage);
		for (int i = 0; i < postList.size(); i++) {

			String filename = fService.findImg(postList.get(i).getPost_id());
			postList.get(i).setFile_name(filename);
		}

		ModelAndView mav = new ModelAndView(cat_name);
		
		mav.addObject("catList", catList);
		mav.addObject("cat_name", cat_name);
		mav.addObject("tagList", tagList);
		mav.addObject("locationList", locationList);
		mav.addObject("postList", postList);
		mav.addObject("pDto", pDto);

		return mav;
	}
	
	@GetMapping(path="/tagSearch")
	public ModelAndView tSearch(@RequestParam("cat_name")String cat_name, @RequestParam("page") int nowPage, TagSearchDto TDto, CitySearchDto CDto,TotalTagSearchDto totDto) {
		int limitPage = 10;
		// 표시할 페이지 숫자
		int pageListLimit = 10;
		// DB시작 지점
		int startRow = (nowPage - 1) * limitPage;
		
		int startPage = ((nowPage - 1) / pageListLimit) * pageListLimit + 1;
		int endPage = startPage + pageListLimit - 1;
		ModelAndView mav = new ModelAndView("tagSearch");
		if(TDto.getTag_name0() == null && TDto.getTag_name1() == null && TDto.getTag_name2() == null && TDto.getTag_name3() == null
				&& TDto.getTag_name4() == null && TDto.getTag_name5() == null && TDto.getTag_name6() == null && TDto.getTag_name7() == null
				&& TDto.getTag_name8() == null && CDto.getCity0() == null && CDto.getCity1() == null && CDto.getCity2() == null && CDto.getCity3() == null &&
				CDto.getCity4() == null && CDto.getCity5() == null && CDto.getCity6() == null &&
				CDto.getCity7() == null && CDto.getCity8() == null) {
			
			List<CategoryDto> catList = mainService.getAllCategory();
			List<LocationDto> locationList = mainService.getAllLocation();
			List<TagDto> tagList = mainService.getTagByCatName(cat_name);
			List<PostDto> pList = mainService.getPostByCatName(cat_name, startRow, limitPage);
			
			int listCount = mainService.getTotalCount(cat_name);
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			PageDto pDto = new PageDto(cat_name,startPage, endPage, nowPage, maxPage, pageListLimit, listCount, limitPage);
			mav.addObject("catList", catList);
			mav.addObject("cat_name", cat_name);
			mav.addObject("tagList", tagList);
			mav.addObject("locationList", locationList);
			mav.addObject("postList", pList);
			mav.addObject("pDto", pDto);
			mav.addObject("tDto", totDto);
			return mav;
		}
		else if(TDto.getTag_name0() == null && TDto.getTag_name1() == null && TDto.getTag_name2() == null && TDto.getTag_name3() == null
				&& TDto.getTag_name4() == null && TDto.getTag_name5() == null && TDto.getTag_name6() == null && TDto.getTag_name7() == null
				&& TDto.getTag_name8() == null) {
			List<CategoryDto> catList = mainService.getAllCategory();
			List<LocationDto> locationList = mainService.getAllLocation();
			List<TagDto> tagList = mainService.getTagByCatName(cat_name);
			CDto.setStartRow(startRow);
			CDto.setLimitPage(limitPage);
			CDto.setCat_name(cat_name);
			List<PostDto> pList = mainService.citySearch(CDto);
			int listCount = mainService.citySearchCnt(CDto);
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			
			PageDto pDto = new PageDto(cat_name,startPage, endPage, nowPage, maxPage, pageListLimit, listCount, limitPage);
			mav.addObject("catList", catList);
			mav.addObject("cat_name", cat_name);
			mav.addObject("tagList", tagList);
			mav.addObject("locationList", locationList);
			mav.addObject("postList", pList);
			mav.addObject("pDto", pDto);
			mav.addObject("tDto", totDto);
			return mav;
		}else if(CDto.getCity0() == null && CDto.getCity1() == null && CDto.getCity2() == null && CDto.getCity3() == null &&
				CDto.getCity4() == null && CDto.getCity5() == null && CDto.getCity6() == null &&
				CDto.getCity7() == null && CDto.getCity8() == null) {
			List<CategoryDto> catList = mainService.getAllCategory();
			List<LocationDto> locationList = mainService.getAllLocation();
			List<TagDto> tagList = mainService.getTagByCatName(cat_name);
			TDto.setStartRow(startRow);
			TDto.setLimitPage(limitPage);
			TDto.setCat_name(cat_name);
			List<PostDto> pList = mainService.tagSearch(TDto);
			
			int listCount = mainService.tagSearchCnt(TDto);
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			PageDto pDto = new PageDto(cat_name,startPage, endPage, nowPage, maxPage, pageListLimit, listCount, limitPage);
			mav.addObject("catList", catList);
			mav.addObject("cat_name", cat_name);
			mav.addObject("tagList", tagList);
			mav.addObject("locationList", locationList);
			mav.addObject("postList", pList);
			mav.addObject("pDto", pDto);
			mav.addObject("tDto", totDto);
			return mav;
		}else {
			List<CategoryDto> catList = mainService.getAllCategory();
			List<LocationDto> locationList = mainService.getAllLocation();
			List<TagDto> tagList = mainService.getTagByCatName(cat_name);
			totDto.setStartRow(startRow);
			totDto.setLimitPage(limitPage);
			totDto.setCat_name(cat_name);
			List<PostDto> pList = mainService.TotalList(totDto);
			
			int listCount = mainService.AllCnt(totDto);
			int maxPage = (int) Math.ceil((double) listCount / limitPage);
			if (endPage > maxPage) {
				endPage = maxPage;
			}
			PageDto pDto = new PageDto(cat_name,startPage, endPage, nowPage, maxPage, pageListLimit, listCount, limitPage);
			mav.addObject("catList", catList);
			mav.addObject("cat_name", cat_name);
			mav.addObject("tagList", tagList);
			mav.addObject("locationList", locationList);
			mav.addObject("postList", pList);
			mav.addObject("pDto", pDto);
			mav.addObject("tDto", totDto);
			return mav;
		}
		
	}


	@GetMapping(path = "/upload.do")
	public String uploadForm() {
		return "fileUpload";
	}

}
