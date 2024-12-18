package com.springboot.tripTrack.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.springboot.tripTrack.dto.ArticleDto;
import com.springboot.tripTrack.dto.BookmarkDto;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.dto.PostDto;
import com.springboot.tripTrack.dto.ReadArticleDto;

public interface PostService {
	int getLastNum();
	void insertPost(ArticleDto ADto);
	void insertLatlng(LatLngDto LatDto);
	ReadArticleDto findById(int post_id);
	List<PostDto> findAllPost(int startRow, int limitPage);
	List<PostDto> findList(String searchValue, int startRow, int limitPage);
	BookmarkDto selectGPS(int post_id);
	int findAllCount();
	int findSearchCount(String searchValue);
	List<PostDto> selectPostListByUserId(String user_id);
	Page<PostDto> findPostsByUserId(String user_id, PageRequest pageRequest);
}
