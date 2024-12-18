package com.springboot.tripTrack.controller;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.tripTrack.FileDto.FileDto;
import com.springboot.tripTrack.dto.BookmarkDto;
import com.springboot.tripTrack.dto.CategoryDto;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.dto.SearchIngPage;
import com.springboot.tripTrack.dto.TrackPostDto;
import com.springboot.tripTrack.fileService.FileService;
import com.springboot.tripTrack.service.BookmarkService;
import com.springboot.tripTrack.service.MainService;
import com.springboot.tripTrack.service.TrackPostService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TrackPostController {
	private final BookmarkService bmService;
	private final TrackPostService tpService;
	private final MainService mainService;
	private final FileService fileService;
	
	//트랙 포스트 리스트 가져오기
	@GetMapping("/trackList")
	public ModelAndView viewTrackList(@RequestParam("page") int nowPage) {
		

		//표시할 게시물 숫자
		int limitPage = 5;
		String searchValue="";
		//표시할 페이지 숫자
		int pageListLimit = 10;
		
		//DB시작 지점
		int startRow = (nowPage -1) * limitPage;

		//시작 페이지
		int startPage = ((nowPage -1) / pageListLimit) * pageListLimit + 1;
		
		//끝 페이지
		int endPage = startPage + pageListLimit -1;
		int listCount = tpService.findAllTrackCount();
		
		//전체 리스트 갯수
		int maxPage = 	(int)Math.ceil((double)listCount / limitPage);
		if(endPage > maxPage)
		{
			endPage = maxPage;
		}
		
		List<TrackPostDto> trackList = tpService.getPagingTrackList(startRow, limitPage);
		SearchIngPage pDto = new SearchIngPage(searchValue ,startPage, endPage, nowPage,maxPage, pageListLimit,listCount, limitPage);
		List<CategoryDto> catList = mainService.getAllCategory();
		ModelAndView mav = new ModelAndView("trackList");
		mav.addObject("catList", catList);
		mav.addObject("trackList", trackList);
		mav.addObject("pDto", pDto);
		
		return mav;
	}
	
	//트랙 포스트 읽기
	@GetMapping("/viewTrackPost")
	public ModelAndView getTrackPostByPostId(@RequestParam("post_id") int id) {
		TrackPostDto tpDto = tpService.getTrackPostByPostId(id);
		List<LatLngDto> gpsList = tpService.getGPSList(tpDto);
		List<CategoryDto> catList = mainService.getAllCategory();
		List<FileDto> fileList = tpService.getFileListByTrackPostId(tpDto);
		String profile = fileService.selectProFile(tpDto.getUser_id());
		ModelAndView mav = new ModelAndView("readTrackPostForm");
		
		mav.addObject("catList", catList);
		mav.addObject("tpDto", tpDto);
		mav.addObject("gpsList", gpsList);
		mav.addObject("fileList", fileList);
		mav.addObject("profile", profile);
		
		return mav;
	}
	
	//새로운 트랙 포스트 작성 페이지, 로그인 안하면 로그인페이지로 이동
	@GetMapping("/new-track")
	public ModelAndView viewTrackPostForm(HttpServletResponse response, Authentication authentication)
			throws Exception {
		ModelAndView mav = new ModelAndView("writeTrackPostForm");
		String userId;
		
		if (authentication != null) {
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			userId = userDetails.getUsername();
			String nick = userDetails.getUsername();
			List<BookmarkDto> bmList = bmService.selectAllBookmarkByUserId(userId);
			List<CategoryDto> catList = mainService.getAllCategory();
			mav.addObject("catList", catList);
			mav.addObject("bmList", bmList);
			mav.addObject("author", nick);
			return mav;

		} else {
			response.setContentType("text/html; charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인을 먼저 해주세요');</script>");
			out.flush();
			mav = new ModelAndView("login");
			return mav;
		}

	}
	
	//새로운 트랙 포스트 저장, 작성 이후 인덱스 페이지로 이동
	@PostMapping("/writeTrackPost")
	public String createTrackPost(Authentication authentication, @ModelAttribute("sampleDTO") TrackPostDto tpDto) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String userId = userDetails.getUsername();
		tpDto.setUser_id(userId);
		
		tpService.insertTrackPost(tpDto);
		
		return "redirect:/";
	}
	

}
