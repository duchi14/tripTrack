package com.springboot.tripTrack.serviceImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dao.MemberDao;
import com.springboot.tripTrack.memberDto.MemberDto;
import com.springboot.tripTrack.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService  {
	private final MemberDao memberDao;
	
	@Autowired
	public MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Override
	public void saveMember(MemberDto memberDto) {
		memberDao.insertMember(memberDto);
	}
	
	@Override
	public List<MemberDto> selectMemberList() {
		return memberDao.selectMemberList();
	}
	
	@Override
	public void updateMember(MemberDto member) throws Exception {
	    memberDao.updateMember(member);
	}

	@Override
	public void deleteMember(String user_id) throws Exception {
		memberDao.deleteMember(user_id);
	}

	@Override
	public int checkId(String user_id) throws Exception  {
		int result = 0;
	        
	    result = memberDao.checkid(user_id);
	    return result;
	}
	
	@Override
	public int checkEmail(String email) throws Exception {
	    int result = 0;
	    
	    result = memberDao.checkEmail(email);
	    return result;
	}
	
	@Override
    public String getPassword(String user_pw) throws Exception {
        return memberDao.getPassword(user_pw);
    }
	
	@Override
	public int checkNickname(String nickname) throws Exception  {
	       int result = memberDao.checkNickname(nickname);

	       return result;
	    }
	@Override
	public String getByUserNickName(String user_id) {
		return memberDao.getByUserNickName(user_id);
	}
	@Override
	public MemberDto getMemberByUserId(String user_id) {
		return memberDao.selectUserByUserId(user_id);
	}
}