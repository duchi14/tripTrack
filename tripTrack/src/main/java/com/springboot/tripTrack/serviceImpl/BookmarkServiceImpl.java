package com.springboot.tripTrack.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dao.BookmarkDao;
import com.springboot.tripTrack.dto.BookmarkDto;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
	private final BookmarkDao bmDao;
	
	// 북마크 개수를 유저당 10개로 제한.
	private boolean chkBookmarkCnt(BookmarkDto dto) {
		Integer cntBookmark = bmDao.cntBookmarkByUserId(dto.getUser_id());
		boolean chk;

		if (cntBookmark >= 10) {
			chk = false;
		} else {
			chk = true;
		}

		return chk;
	}

	// 북마크 중복검사
	private boolean chkDupePostId(BookmarkDto dto) {
		boolean chk = true;
		// 추가되는 북마크 포스트아이디 저장
		int newPostId = dto.getPost_id();
		List<BookmarkDto> list = selectAllBookmarkByUserId(dto.getUser_id());

		for (int i = 0; i < list.size(); i++) {
			// 기존에 가지고 있는 북마크 포스트아이디 저장
			BookmarkDto bmDto = new BookmarkDto();
			bmDto = list.get(i);
			int markedPostId = bmDto.getPost_id();
			
			//중복 될 경우 탈출 및 false 리턴
			if (newPostId == markedPostId) {
				chk = false;
				break;
			}
		}

		return chk;
	}
	
	//포스트아이디로 gps정보 리턴
	public LatLngDto selectGpsByPostId(Integer post_id) {
		LatLngDto gps = bmDao.selectGpsByPostId(post_id);
		return gps;
	}

	// 유저 아이디로 북마크리스트 리턴
	public List<BookmarkDto> selectAllBookmarkByUserId(String user_id) {

		return bmDao.selectAllBmByUserId(user_id);
	}
	
	// 유저 아이디로 북마크한 포스트아이디 리스트 리턴
	private List<Integer> selectBookmarkedPostIdByUserId(String user_id) {
		
		return bmDao.selectBookmarkedPostIdByUserId(user_id); 
	}
	//북마크 유저아이디로 체크여부 리턴
	public boolean isBookmarked(String user_id, Integer post_id) {
		boolean chk = false;
		List<Integer> bookmarkedPostIdList =  selectBookmarkedPostIdByUserId(user_id);
		for(int bookmarkedPostId : bookmarkedPostIdList) {
			if(bookmarkedPostId == post_id) {
				chk = true;
				break;
			}
		}
		return chk;
	}
	
	// 북마크를 추가
	public boolean createBookmark(BookmarkDto dto) {
		boolean chk;

		if (chkBookmarkCnt(dto) && chkDupePostId(dto)) {
			bmDao.insertBookmark(dto);
			chk = true;
		} else {
			chk = false;
		}

		return chk;
	}
	//북마크 삭제
	public boolean deleteBookmark(String user_id, int post_id) {
		boolean chk;
		System.out.println(user_id);
		if(bmDao.deleteBookmark(user_id, post_id)) {
			chk = true;
		} else {
			chk = false;
		}
		return chk;
	}
}
