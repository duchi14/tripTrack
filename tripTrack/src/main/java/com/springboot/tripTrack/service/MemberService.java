package com.springboot.tripTrack.service;

import java.util.List;

import com.springboot.tripTrack.memberDto.MemberDto;

public interface MemberService {
	void saveMember(MemberDto memberDto);
	List<MemberDto> selectMemberList();
	void updateMember(MemberDto member) throws Exception;
	void deleteMember(String user_id) throws Exception;
	int checkId(String user_id) throws Exception;
	int checkEmail(String email) throws Exception;
	String getPassword(String user_pw) throws Exception;
	int checkNickname(String nickname) throws Exception;
	String getByUserNickName(String user_id);
	MemberDto getMemberByUserId(String user_id);
}
