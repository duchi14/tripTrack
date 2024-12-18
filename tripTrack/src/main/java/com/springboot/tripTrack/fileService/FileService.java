package com.springboot.tripTrack.fileService;

import java.util.List;

import com.springboot.tripTrack.FileDto.FileDto;
import com.springboot.tripTrack.dto.ReadArticleDto;




public interface FileService {
	void uploadFile(FileDto fileDto);
	
	List<FileDto> getFileList();
	
	FileDto getFileName(int id);
	
	void fileDelete(FileDto fileDto);
	
	String findImg(int post_id);
	
	List<ReadArticleDto> findFileList(int id);
	void uploadProfile(FileDto dto);
	
	String selectProFile(String user_id);
	
	void deleteProFile(String user_id);

}
