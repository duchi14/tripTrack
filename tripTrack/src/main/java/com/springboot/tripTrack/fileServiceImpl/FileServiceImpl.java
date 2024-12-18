package com.springboot.tripTrack.fileServiceImpl;

import java.util.List;



import org.springframework.stereotype.Service;

import com.springboot.tripTrack.FileDto.FileDto;
import com.springboot.tripTrack.dto.ArticleDto;
import com.springboot.tripTrack.dto.LatLngDto;
import com.springboot.tripTrack.dto.ReadArticleDto;
import com.springboot.tripTrack.dto.ReviewDto;
import com.springboot.tripTrack.fileDao.FileDao;
import com.springboot.tripTrack.fileService.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
	private final FileDao fileDao;

	@Override
	public void uploadFile(FileDto fileDto) {
		fileDao.insertFile(fileDto);
		// TODO Auto-generated method stub

	}

	@Override
	public List<FileDto> getFileList() {
		// TODO Auto-generated method stub
		return fileDao.selectFile();
	}

	@Override
	public FileDto getFileName(int id) {
		// TODO Auto-generated method stub
		return fileDao.selectFileNameById(id);
	}
	
	@Override
	public void fileDelete(FileDto fileDto) {
		int potoId = fileDto.getPost_id();
		fileDao.deleteFile(potoId);
	}
	
	@Override
	public void uploadProfile(FileDto dto) {
		 fileDao.uploadProfile(dto);
	}

	
	@Override
	public String findImg(int post_id) {
		return fileDao.findImg(post_id);
	}
	@Override
	public List<ReadArticleDto> findFileList(int id){
		List<ReadArticleDto> list = fileDao.findFile(id);
		return list;
	}
	
	@Override
	public String selectProFile(String user_id) {
		return fileDao.selectProFile(user_id);
	}
	
	@Override
	public void deleteProFile(String user_id) {
		fileDao.deleteProFile(user_id);
	}
	
}
