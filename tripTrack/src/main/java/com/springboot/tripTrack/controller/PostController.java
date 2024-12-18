package com.springboot.tripTrack.controller;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.tripTrack.dto.CategoryDto;
import com.springboot.tripTrack.dto.LocationDto;
import com.springboot.tripTrack.dto.ReadArticleDto;
import com.springboot.tripTrack.dto.ReviewDto;
import com.springboot.tripTrack.dto.TagDto;
import com.springboot.tripTrack.fileService.FileService;
import com.springboot.tripTrack.service.LocationService;
import com.springboot.tripTrack.service.MainService;
import com.springboot.tripTrack.service.MemberService;
import com.springboot.tripTrack.service.PostService;
import com.springboot.tripTrack.service.ReviewService;
import com.springboot.tripTrack.service.StarService;
import com.springboot.tripTrack.service.TagService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PostController {
	private final MainService mainService;
	private final TagService tagService;
	private final LocationService locService;
	private final PostService pService;
	private final FileService fService;
	private final ReviewService rService;
	private final StarService sService;
	private final MemberService mService;
	@GetMapping("/Viewarticles")
	public ModelAndView getArticle(@RequestParam("post_id") int id,  Authentication authentication ) {
		ModelAndView mav;
		
		if(authentication != null) {
			ReadArticleDto rDto = pService.findById(id);
			List<CategoryDto> catList = mainService.getAllCategory();
			List<ReadArticleDto> fileList = fService.findFileList(id);
			List<ReviewDto> reviewList = rService.findAllreview(id);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String nick = userDetails.getUsername();
			double avg_star = sService.avg_star(id);
			
			mav = new ModelAndView("ReadArticle");
			mav.addObject("catList", catList);
			mav.addObject("rDto", rDto);
			mav.addObject("fileList", fileList);
			mav.addObject("reviewList", reviewList);
			mav.addObject("avg", avg_star);
			mav.addObject("author", nick);

			return mav;

		}else {
			ReadArticleDto rDto = pService.findById(id);
			List<CategoryDto> catList = mainService.getAllCategory();
			List<ReadArticleDto> fileList = fService.findFileList(id);
			List<ReviewDto> reviewList = rService.findAllreview(id);
			double avg_star = sService.avg_star(id);
			
			mav = new ModelAndView("ReadArticle");
			mav.addObject("catList", catList);
			mav.addObject("rDto", rDto);
			mav.addObject("fileList", fileList);
			mav.addObject("reviewList", reviewList);
			mav.addObject("avg", avg_star);

			return mav;
		}
		
		
		
		
	}
	
	@GetMapping("/new-article")
	public ModelAndView insertArticleForm(HttpServletResponse response, Model model, Authentication authentication) throws Exception {
		ModelAndView mav;
		
		if(authentication != null) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String nick = mService.getByUserNickName(userDetails.getUsername());
			model.addAttribute("author",nick);
		}else {
			response.setContentType("text/html; charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인을 먼저 해주세요');</script>");
			out.flush();
			mav = new ModelAndView("login");
			return mav;
		}
		List<CategoryDto> catList = mainService.getAllCategory();
		List<TagDto> tagList = tagService.getAllTag();
		List<LocationDto> locList = locService.getAllLoc();
		
		mav = new ModelAndView("newArticle");
		
		mav.addObject("locList", locList);
		mav.addObject("tagList", tagList);
		mav.addObject("catList", catList);
		return mav;
	}
}
