package com.springboot.tripTrack.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dao.PostDao;
import com.springboot.tripTrack.dto.ArticleDto;
import com.springboot.tripTrack.dto.BookmarkDto;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.ReadArticleDto;
import com.springboot.tripTrack.fileService.FileService;
import com.springboot.tripTrack.service.PostService;
import com.springboot.tripTrack.service.StarService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostDao pDao;
	private final StarService sService;
	private final FileService fService;

	public int getLastNum() {
		int post_id;
		if (pDao.LastNum() == null) {
			post_id = 1;
		} else {
			post_id = Integer.parseInt(pDao.LastNum()) + 1;
		}
		return post_id;
	}

	public ReadArticleDto findById(int id) {
		ReadArticleDto rDto = pDao.findById(id);

		return rDto;
	}

	public void insertPost(ArticleDto ADto) {
		
		pDao.insertPost(ADto);

	}

	public void insertLatlng(LatLngDto LatDto) {
		
		pDao.insertLatlng(LatDto);
	}
	
	public int findAllCount() {
		return pDao.findAllCount();
	}
	
	public int findSearchCount(String searchValue) {
		return pDao.findSearchCount(searchValue);
	}
	
	public List<PostDto> findAllPost(int startRow, int limitPage){
		List<PostDto> list = pDao.findAllPost(startRow, limitPage);
		for(int i =0; i < list.size(); i++) {
			double avg_star;
			int cnt_star;
			String file_name;
			avg_star = sService.avg_star(list.get(i).getPost_id());
			cnt_star = sService.cnt_star(list.get(i).getPost_id());
			file_name = fService.findImg(list.get(i).getPost_id());
			list.get(i).setFile_name(file_name);
			list.get(i).setAvg_star(avg_star);
			list.get(i).setCnt_star(cnt_star);
		}

		return list;
	}
	
	public List<PostDto> findList(String searchValue, int startRow, int limitPage){
		List<PostDto> list = pDao.findList(searchValue, startRow, limitPage);
		
		for(int i =0; i < list.size(); i++) {
			double avg_star;
			int cnt_star;
			String file_name;
			avg_star = sService.avg_star(list.get(i).getPost_id());
			cnt_star = sService.cnt_star(list.get(i).getPost_id());
			file_name = fService.findImg(list.get(i).getPost_id());
			list.get(i).setFile_name(file_name);
			list.get(i).setAvg_star(avg_star);
			list.get(i).setCnt_star(cnt_star);
		}
		return list;
	}
	
	public BookmarkDto selectGPS(int post_id) {
		return pDao.selectGPS(post_id);
	}
	
	public List<PostDto> selectPostListByUserId(String user_id) {
		return pDao.selectPostListByUserId(user_id);
	}
	//페이징
    public Page<PostDto> findPostsByUserId(String userId, PageRequest pageRequest) {
        int limit = pageRequest.getPageSize();
        int offset = (int) pageRequest.getOffset();
        List<PostDto> postList = pDao.findPostsByUserId(userId, limit, offset);
        int total = pDao.countPostsByUserId(userId);
        
        return new PageImpl<>(postList, pageRequest, total);
    }
}
