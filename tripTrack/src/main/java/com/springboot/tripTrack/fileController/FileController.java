package com.springboot.tripTrack.fileController;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.tripTrack.FileDto.FileDto;
import com.springboot.tripTrack.dto.ArticleDto;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.fileService.FileService;
import com.springboot.tripTrack.memberDto.MemberDto;
import com.springboot.tripTrack.service.MemberService;
import com.springboot.tripTrack.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FileController {
	private final FileService service;
	private final PostService Postservice;
	private final MemberService memberService;	
	private String REPOSITORY_DIR = "/Users/duchi/Documents/spring/repository/";
	private String REPOSITORY_PROFILE = "/Users/duchi/Documents/spring/repository/profile/";
	//private String REPOSITORT_DIR = "C:\\spring\\repository\\";
	//private String REPOSITORT_PROFILE = "C:\\spring\\repository\\profile\\";
	
	

	@PostMapping("/upload")
	public String fileUpload(@RequestParam("file") List<MultipartFile> fileList, ArticleDto ADto, LatLngDto LatDto) {
		List<Map<String, String>> multifileList = new ArrayList<>();
		int post_id = Postservice.getLastNum();
		File Folder = new File(REPOSITORY_DIR+post_id);
			if(!Folder.exists()) {
				try {
					Folder.mkdir();
					System.out.println("폴더가 생성되었습니다.");
				}catch(Exception e) {
					e.getStackTrace();
				}
			}else {
				System.out.println("이미 폴더가 생성되어 있습니다.");
			}
			
			if(fileList.isEmpty()) {
				
				return "redirect:/new-article";
			}try {
				
				for(int i = 0; i <fileList.size(); i++) {
					FileDto dto = new FileDto();
					String OriginName = fileList.get(i).getOriginalFilename();
					String fileName = UUID.randomUUID().toString() + "_" + fileList.get(i).getOriginalFilename();
					
					Map<String, String> map = new HashMap<>();
					map.put("OriginName", OriginName);
					map.put("fileName", fileName);
					
					multifileList.add(map);
					dto.setPost_id(post_id);
					dto.setFile_name(fileName);
					dto.setFile_path(REPOSITORY_DIR);
					dto.setOrg_file_name(fileList.get(i).getOriginalFilename());
					service.uploadFile(dto);
				}
				
				for(int i = 0; i < fileList.size(); i++) {
					File uploadFile = new File(REPOSITORY_DIR+post_id+"\\"+multifileList.get(i).get("fileName"));
					fileList.get(i).transferTo(uploadFile);
				}
				ADto.setPost_id(post_id);
				LatDto.setPost_id(post_id);
				Postservice.insertPost(ADto);
				Postservice.insertLatlng(LatDto);
			}catch(IOException e) {
				e.printStackTrace();
			}
			return "redirect:/Viewarticles?post_id=" + post_id;
	
		
	}
	
	@PostMapping("/profile")
	public ModelAndView uploadProfile(@RequestParam("file") MultipartFile file, Principal principal, HttpSession session) {
		ModelAndView mav = new ModelAndView("myPage");
		String user_id = principal.getName();
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		File Folder = new File(REPOSITORY_PROFILE+"\\"+user_id);
		
		if(!Folder.exists()) {
			try {
				Folder.mkdir();
				System.out.println("폴더가 생성되었습니다.");
			}catch(Exception e) {
				e.getStackTrace();
			}
		}else {
			System.out.println("이미 폴더가 생성되어 있습니다.");
		}
		try {
			
			if(null != service.selectProFile(user_id)) {
				String oldFile = service.selectProFile(user_id);
				fileDelete(oldFile,user_id);
				service.deleteProFile(user_id);
				
				
				MemberDto member = memberService.getMemberByUserId(user_id);
				FileDto dto = new FileDto();
				dto.setFile_name(fileName);
				dto.setFile_path(REPOSITORY_PROFILE+user_id);
				
				dto.setUser_id(user_id);
				dto.setOrg_file_name(file.getOriginalFilename());
				file.transferTo(new File(REPOSITORY_PROFILE+user_id+"\\" + fileName));
				service.uploadProfile(dto);
				
				mav.addObject("message", "파일 업로드에 성공했습니다.");
				session.setAttribute("profile",fileName);
				mav.addObject("fileName", fileName);
				mav.addObject("user_id", user_id);
				mav.addObject("member", member);
			}else {
				MemberDto member = memberService.getMemberByUserId(user_id);
				FileDto dto = new FileDto();
				dto.setFile_name(fileName);
				dto.setFile_path(REPOSITORY_PROFILE+user_id);
				
				dto.setUser_id(user_id);
				dto.setOrg_file_name(file.getOriginalFilename());
				file.transferTo(new File(REPOSITORY_PROFILE+user_id+"\\" + fileName));
				
				service.uploadProfile(dto);
				
				mav.addObject("message", "파일 업로드에 성공했습니다.");
				session.setAttribute("profile",fileName);
				mav.addObject("fileName", fileName);
				mav.addObject("user_id", user_id);
				mav.addObject("member", member);
			}
			
			return mav;
			
		}catch(IOException e) {
			e.printStackTrace();
			mav.addObject("message", "파일이 전송이 실패했습니다.");
			mav.addObject("fileName", fileName);
		return mav;
		}
	}
	
	@PostMapping("/delete")
	public void fileDelete(String oldFile, String user_id) {
		File file = new File(REPOSITORY_PROFILE+user_id+"\\"+ oldFile);
		boolean result = file.delete();
	}
}
