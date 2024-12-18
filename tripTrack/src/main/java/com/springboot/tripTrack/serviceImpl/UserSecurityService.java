package com.springboot.tripTrack.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.tripTrack.dao.MemberDao;
import com.springboot.tripTrack.member.role.UserRole;
import com.springboot.tripTrack.memberDto.MemberDto;
import com.springboot.tripTrack.service.EmailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{
	private final MemberDao dao;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final EmailService emailService;
	
	public MemberDto create(MemberDto dto) {
		MemberDto user = new MemberDto();
		user.setUser_id(dto.getUser_id());
		user.setUser_pw(passwordEncoder.encode(dto.getUser_pw()));
		user.setEmail(dto.getEmail());
		user.setNickname(dto.getNickname());
		user.setAddr(dto.getAddr());
		user.setGender(dto.getGender());
		user.setTel(dto.getTel());
		this.dao.insertMember(user);
		return user;
	}
	
	@Override
	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
		MemberDto member = this.dao.getByUserId(user_id);
		if(member == null) {
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		if("admin".equals(user_id)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		}
		else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		return new User(member.getUser_id(), member.getUser_pw(), authorities);
	}
	
	 // 이 메서드는 이메일을 사용하여 아이디를 찾습니다.
    public String findUsernameByEmail(String email) {
        // 데이터베이스에서 이메일로 사용자 검색
        MemberDto user = dao.findByEmail(email);
        return user != null ? user.getUser_id() : null;
    }

    // 이 메서드는 아이디와 이메일을 사용하여 비밀번호를 초기화합니다.
    public boolean resetPassword(String user_id, String email) {
        MemberDto dto = dao.findByUsernameAndEmail(user_id, email);
        if (dto != null) {
            // 비밀번호 초기화 로직 (예: 랜덤 비밀번호 생성 및 이메일 발송)
            String newPassword = generateRandomPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            dto.setUser_pw(encodedPassword);
            dao.save(dto);
            emailService.sendPasswordResetEmail(dto.getEmail(), newPassword);
            return true;
        }
        return false;
    }

    private String generateRandomPassword() {
        // 랜덤 비밀번호 생성 로직
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
