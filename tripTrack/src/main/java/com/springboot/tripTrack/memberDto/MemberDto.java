package com.springboot.tripTrack.memberDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
	private String user_id;
	private String user_pw;
	private String email;
	private String nickname;
	private String addr;
	private String gender;
	private String tel;
	
}
