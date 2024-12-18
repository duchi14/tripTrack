package com.springboot.tripTrack.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dao.MainDao;
import com.springboot.tripTrack.dto.BestPostDto;
import com.springboot.tripTrack.dto.CategoryDto;
import com.springboot.tripTrack.dto.CitySearchDto;
import com.springboot.tripTrack.dto.LocationDto;
import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.TagDto;
import com.springboot.tripTrack.dto.TagSearchDto;
import com.springboot.tripTrack.dto.TotalTagSearchDto;
import com.springboot.tripTrack.fileService.FileService;

@Service
public class MainService {
	private final MainDao mainDao;
	private final StarService sService;
	private final FileService fService;

	@Autowired
	public MainService(MainDao mainDao, StarService sService, FileService fService) {
		this.mainDao = mainDao;
		this.sService = sService;
		this.fService = fService;
	}
	
	public List<CategoryDto> getAllCategory() {
		List<CategoryDto> catList = mainDao.selectAllCategory();
		
		return catList;
	}

	public List<LocationDto> getAllLocation() {
		List<LocationDto> locationList = mainDao.selectAllLocation();

		return locationList;
	}

	public List<TagDto> getTagByCatName(String cat_name) {
		List<TagDto> tagList = mainDao.selectTagByCatName(cat_name);


		return tagList;
	}
	

	public List<PostDto> getPostByCatName(String cat_name, int startRow, int limitPage) {
		List<PostDto> placePostList = mainDao.selectPostByCatName(cat_name, startRow, limitPage);
		List<PostDto> placePostList2 = new ArrayList<>();
		
		for(int i = 0; i < placePostList.size(); i++) {
			PostDto dto = new PostDto();
			dto = placePostList.get(i);
			dto.setAvg_star((Math.round(dto.getAvg_star()*10)/10)/2.0);
			String filename = fService.findImg(placePostList.get(i).getPost_id());
			dto.setFile_name(filename);
			placePostList2.add(dto);
		}

		return placePostList2;
	}

	public List<BestPostDto> getBestPlacePost() {
		
		List<BestPostDto> bpList = mainDao.selectBestPostByCatName("place");
		for(int i =0; i < bpList.size(); i++) {
			double avg_star = sService.avg_star(bpList.get(i).getPost_id());
			bpList.get(i).setAvg_star(avg_star);
			String filename = fService.findImg(bpList.get(i).getPost_id());
			bpList.get(i).setFile_name(filename);
		}
		return bpList;
	}
	
	public List<BestPostDto> getBestAccPost() {
		List<BestPostDto> baList = mainDao.selectBestPostByCatName("acc");
		for(int i =0; i < baList.size(); i++) {
			double avg_star = sService.avg_star(baList.get(i).getPost_id());
			baList.get(i).setAvg_star(avg_star);
			String filename = fService.findImg(baList.get(i).getPost_id());
			baList.get(i).setFile_name(filename);
		}
		return baList;
	}
	
	public List<BestPostDto> getRestPost() {
		List<BestPostDto> brList = mainDao.selectBestPostByCatName("rest");
		for(int i =0; i < brList.size(); i++) {
			double avg_star = sService.avg_star(brList.get(i).getPost_id());
			brList.get(i).setAvg_star(avg_star);
			String filename = fService.findImg(brList.get(i).getPost_id());
			brList.get(i).setFile_name(filename);
		}
		return brList;
	}

	public int getTotalCount(String cat_name) {
		return mainDao.totalCount(cat_name);
	}
	
	public List<PostDto> AllList(String cat_name, int startRow, int limitPage){
		List<PostDto> AllList = mainDao.AllList(cat_name, startRow,limitPage);
		System.out.println("진입:" + AllList);
		List<PostDto> AllList2 = new ArrayList<>();
		for(int i = 0; i < AllList.size(); i++) {
			PostDto Pdto = new PostDto();
			Pdto = AllList.get(i);
			String filename = fService.findImg(AllList.get(i).getPost_id());
			Pdto.setFile_name(filename);
			Pdto.setAvg_star(sService.avg_star(AllList.get(i).getPost_id()));
			Pdto.setCnt_star(sService.cnt_star(AllList.get(i).getPost_id()));
			
			AllList2.add(Pdto);
		}
		
		return AllList2;
	}
	
	public int AllListCnt(String cat_name) {
		return AllListCnt(cat_name);
	}
	
	public int tagSearchCnt(TagSearchDto dto) {
		return mainDao.tagSearchCnt(dto);
	}
	
	public int citySearchCnt(CitySearchDto CDto) {
		return mainDao.citySearchCnt(CDto);
	}
	
	public List<PostDto> tagSearch(TagSearchDto dto){
		List<PostDto> tagList = mainDao.tagSearch(dto);
		List<PostDto> tagList2 = new ArrayList<>();
		
		for(int i = 0; i < tagList.size(); i++) {
			PostDto Pdto = new PostDto();
			Pdto = tagList.get(i);
			String filename = fService.findImg(tagList.get(i).getPost_id());
			Pdto.setFile_name(filename);
			Pdto.setAvg_star(sService.avg_star(tagList.get(i).getPost_id()));
			Pdto.setCnt_star(sService.cnt_star(tagList.get(i).getPost_id()));
			
			tagList2.add(Pdto);
		}
		
		return tagList2;
	}
	
	public List<PostDto> citySearch(CitySearchDto CDto){
		List<PostDto> cList = mainDao.citySearch(CDto);
		List<PostDto> cList2 = new ArrayList<>();
		for(int i = 0; i < cList.size(); i++) {
			PostDto Pdto = new PostDto();
			Pdto = cList.get(i);
			String filename = fService.findImg(cList.get(i).getPost_id());
			Pdto.setFile_name(filename);
			Pdto.setAvg_star(sService.avg_star(cList.get(i).getPost_id()));
			Pdto.setCnt_star(sService.cnt_star(cList.get(i).getPost_id()));
			
			cList2.add(Pdto);
		}
		
		return cList2;
	}
	
	public List<PostDto> TotalList(TotalTagSearchDto dto){
		
		List<PostDto> TList  = mainDao.TotalList(dto);
		List<PostDto> TList2 = new ArrayList<>();
		for(int i = 0; i < TList.size(); i++) {
			PostDto Pdto = new PostDto();
			Pdto = TList.get(i);
			String filename = fService.findImg(TList.get(i).getPost_id());
			Pdto.setFile_name(filename);
			Pdto.setAvg_star(sService.avg_star(TList.get(i).getPost_id()));
			Pdto.setCnt_star(sService.cnt_star(TList.get(i).getPost_id()));
			
			TList2.add(Pdto);
		}
		
		return TList2;
	}
	
	public int AllCnt(TotalTagSearchDto dto) {
		return mainDao.AllCnt(dto);
	}
}
