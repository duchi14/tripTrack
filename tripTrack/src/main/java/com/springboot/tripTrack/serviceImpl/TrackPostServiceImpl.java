package com.springboot.tripTrack.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.tripTrack.FileDto.FileDto;
import com.springboot.tripTrack.dao.TrackPostDao;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.dto.TrackPostDto;
import com.springboot.tripTrack.fileDao.FileDao;
import com.springboot.tripTrack.fileService.FileService;
import com.springboot.tripTrack.service.TrackPostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrackPostServiceImpl implements TrackPostService {

	private final TrackPostDao tpDao;
	private final FileService fService;
	private final FileDao fileDao;

	private int getLastPostNum() {

		Integer post_id = tpDao.getLastPostNum();

		if (post_id == null) {
			post_id = 1;
		} else {
			post_id += 1;
		}

		return post_id;
	}
	@Override
	public boolean insertTrackPost(TrackPostDto tpDto) {
		boolean chk = false;

		tpDto.setTrackPost_id(getLastPostNum());

		if (tpDao.insertTrackPost(tpDto)) {
			chk = true;
		}

		return chk;
	}
	
	@Override
	public int findAllTrackCount() {
		return tpDao.findAllTrackCount();
	}
	
	@Override
	public List<TrackPostDto> findTrackList(String searchValue, int startRow, int limitPage){
		List<TrackPostDto> list = tpDao.findTrackList(searchValue, startRow, limitPage);
		for(int i =0; i < list.size(); i++) {
			String file_name = fService.selectProFile(list.get(i).getUser_id());
			list.get(i).setProfile(file_name);
		}
		System.out.println(list);
		return list;
		
		
	}
	@Override
	public int findTrackListCount(String searchVale) {
		return tpDao.findTrackListCount(searchVale);
	}
	
	@Override
	public List<TrackPostDto> findAllTrackPost(int startRow, int limitPage){
		List<TrackPostDto> list = tpDao.findAllTrackPost(startRow, limitPage);
		
		return list;
	}

	@Override
	public List<TrackPostDto> getAllTrackList(int startRow, int limitPage) {

		return tpDao.selectAllTrackList(startRow, limitPage);
	}
	@Override
	public TrackPostDto getTrackPostByPostId(int id) {
		TrackPostDto tpDto = tpDao.selectTrackPostByPostId(id);
		getGPSList(tpDto);
		return tpDto;
	}
	@Override
	public List<LatLngDto> getGPSList(TrackPostDto tpDto) {
		List<LatLngDto> gpsList = new ArrayList<>();

		String lat = tpDto.getLat();
		String lng = tpDto.getLng();
		String placeName = tpDto.getPlaceName();
		
		 // null 체크 후 빈 문자열로 처리하거나 적절한 값을 사용
	    if (lat == null) lat = "";
	    if (lng == null) lng = "";
	    if (placeName == null) placeName = "";
		
		String[] latArray = lat.split(",");
		String[] lngArray = lng.split(",");
		String[] placeNameArray = placeName.split(",");
		
		int length = Math.min(latArray.length, Math.min(lngArray.length, placeNameArray.length));

		for (int i = 0; i < latArray.length; i++) {
			LatLngDto gps = new LatLngDto();

			gps.setLat(latArray[i]);
			gps.setLng(lngArray[i]);
			gps.setPlaceName(placeNameArray[i]);
			
			gpsList.add(gps);
		}

		return gpsList;
	}
	
	// 모든 이미지파일 가져오기
		public List<FileDto> getFileListByTrackPostId(TrackPostDto tpDto) {
			List<Integer> postIdList = getPostIdListToTrackPost(tpDto);
			return getFileListByPostIdList(postIdList);
		}
		// 포스트 아이디 list에서 맨 앞것만 가져오기
		private int getFirstPostIdToPostIdList(TrackPostDto tpDto) {
			List<Integer> intList = getPostIdListToTrackPost(tpDto);
			int postId;
			if(intList == null || intList.isEmpty()) {
				postId = -1;
			} else {
				postId = intList.get(0);
				tpDto.setImgFilePostId(postId);
			}
					
			return postId;
		}

		// 포스트아이디 Integer list로 변환
		private List<Integer> getPostIdListToTrackPost(TrackPostDto tpDto) {
			List<Integer> intList = new ArrayList<Integer>();

			if (tpDto.getPost_id() == null) {
				return null;
			} else {
				intList = Arrays.stream(tpDto.getPost_id().split(",")).map(Integer::parseInt).collect(Collectors.toList());
			}

			return intList;
		}

		// 트랙포스트 안에 들어있는 이미지리스트를 하나로 병합
		private List<FileDto> getFileListByPostIdList(List<Integer> postIdList) {
			List<FileDto> fileList = new ArrayList<FileDto>(); // 비어있는 파일리스트 생성
			
			if (postIdList == null) {
				return null;
			} else {
				for (int postId : postIdList) { // 포스트아이디리스트 순회
					fileList.addAll(getFileByPostId(postId));
				}
			}
			
			return fileList;
		}

		// 각 포스트에 들어있는 이미지 파일들을 리스트로 반환
		private List<FileDto> getFileByPostId(int postId) {
			List<FileDto> fileList = fileDao.selectFileByPostId(postId);
			for (FileDto file : fileList) {
				file.setPost_id(postId);
			}
			return fileList;
		}

		// 리스트에 프로필, 사진 추가
		private List<TrackPostDto> profileAddToTrackList(List<TrackPostDto> trackList) {
			for (TrackPostDto track : trackList) {
				String userprofile = fileDao.selectProFile(track.getUser_id());
				track.setProfile(userprofile);	
				
				String imgFile = fileDao.findImg(getFirstPostIdToPostIdList(track));
				if(imgFile == null) {
					track.setImgFile(null);
				} else {
					track.setImgFile(imgFile);
				}
				
			}
			return trackList;
		}
	
	@Override
	public List<TrackPostDto> getAllTrackList() {
		List<TrackPostDto> trackList = tpDao.selectAllTrack();

		return profileAddToTrackList(trackList);
	}
	
	@Override
	public List<TrackPostDto> selectTrackListByUserId(String user_id) {
		return tpDao.selectTrackListByUserId(user_id); 
	}
	//페이징
	@Override
    public Page<TrackPostDto> findTrackPostsByUserId(String userId, PageRequest pageRequest) {
        int limit = pageRequest.getPageSize();
        int offset = (int) pageRequest.getOffset();
        List<TrackPostDto> postList = tpDao.findPostsByUserId(userId, limit, offset);
        int total = tpDao.countPostsByUserId(userId);
        
        return new PageImpl<>(postList, pageRequest, total);
    }
	
	///////////////////////////////////////////////////////////////
	@Override
	public List<TrackPostDto> getPagingTrackList(int startRow, int limitPage) {
		List<TrackPostDto> trackList =  tpDao.selectPaigingTrackList(startRow, limitPage);
		return profileAddToTrackList(trackList);
	};
}
