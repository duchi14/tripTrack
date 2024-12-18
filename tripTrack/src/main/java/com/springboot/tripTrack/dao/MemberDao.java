package com.springboot.tripTrack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataAccessException;

import com.springboot.tripTrack.memberDto.MemberDto;

@Mapper
public interface MemberDao {
	@Select("select * from tbl_member where user_id=#{user_id}")
	public MemberDto getByUserId(String user_id);

	@Insert("insert into tbl_member values(#{user_id}, #{user_pw}, #{email}, #{nickname}, #{addr}, #{gender}, #{tel})")
	public void insertMember(MemberDto memberDto) throws DataAccessException;

	@Select("select * from tbl_member")
	public List<MemberDto> selectMemberList() throws DataAccessException;

	@Update("update tbl_member set user_pw = #{user_pw}, email = #{email}, nickname = #{nickname}, addr = #{addr}, tel = #{tel} where user_id = #{user_id}")
	public void updateMember(MemberDto member) throws DataAccessException;

	@Delete("delete from tbl_member where user_id = #{user_id}")
	public void deleteMember(String user_id) throws DataAccessException;

	@Select("select count(*) from tbl_member where user_id = #{user_id}")
	public int checkid(String user_id) throws DataAccessException;

	@Select("select count(email) from tbl_member where email = #{email}")
	public int checkEmail(String email) throws DataAccessException;

	@Select("select user_pw from tbl_member where user_id = #{user_id}")
	public String getPassword(String user_pw) throws DataAccessException;

	@Select("select count(nickname) from tbl_member where nickname = #{nickname}")
	public int checkNickname(String nickname) throws DataAccessException;

	@Select("select nickname from tbl_member where user_id=#{user_id}")
	public String getByUserNickName(String user_id) throws DataAccessException;

	@Select("select * from tbl_member where user_id=#{user_id}")
	public MemberDto selectUserByUserId(String user_id) throws DataAccessException;
	
	@Select("select * from tbl_member where email = #{email}")
	public MemberDto findByEmail(@Param("email") String email);

	@Select("select* from tbl_member where user_id = #{user_id} and email = #{email}")
	public MemberDto findByUsernameAndEmail(@Param("user_id") String user_id, @Param("email") String email);

	@Update("update tbl_member set user_pw = #{user_pw} where user_id = #{user_id}")
	public void save(MemberDto dto);
}