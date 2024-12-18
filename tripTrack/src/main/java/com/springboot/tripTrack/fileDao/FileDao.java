package com.springboot.tripTrack.fileDao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import com.springboot.tripTrack.FileDto.FileDto;
import com.springboot.tripTrack.dto.ReadArticleDto;


@Mapper
public interface FileDao {
	@Insert("insert into tbl_file(post_id, file_name,file_path, org_file_name) values(#{post_id},#{file_name}, #{file_path},#{org_file_name})")
	public void insertFile(FileDto file);
	
	@Insert("insert into tbl_mulit_file(file_name,file_path, org_file_name) values(#{file_name}, #{file_path},#{org_file_name})")
	public void insertMultiFile(FileDto file);
	
	@Select("select * from tbl_file")
	public List<FileDto> selectFile();
	
	@Select("select file_name, org_file_name from tbl_file where id = #{id}")
	public FileDto selectFileNameById(int id);
	
	@Delete("Delete from tbl_file where id = #{id}")
	public void deleteFile(int id);
	
	@Select("select file_name from tbl_file where post_id = #{post_id} order by id asc limit 1")
	public String findImg(int post_id) throws DataAccessException;
	
	@Select("select file_name from tbl_file where post_id = #{post_id}")
	public List<ReadArticleDto> findFile(int post_id) throws DataAccessException;
	
	@Insert("insert into tbl_profile(user_id, file_name,file_path,org_file_name) values(#{user_id},#{file_name}, #{file_path},#{org_file_name})")
	public void uploadProfile(FileDto dto);
	
	@Select("select file_name from tbl_profile where user_id = #{user_id}")
	public String selectProFile(String user_id) throws DataAccessException;
	
	@Delete("Delete from tbl_profile where user_id = #{user_id}")
	public void deleteProFile(String user_id) throws DataAccessException;
	
	@Select("select file_name from tbl_file where post_id = #{post_id}")
	public List<FileDto> selectFileByPostId(int post_Id) throws DataAccessException;
	
}
