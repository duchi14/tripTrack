package com.springboot.tripTrack.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.tripTrack.fileService.FileService;
import com.springboot.tripTrack.memberDto.MemberDto;
import com.springboot.tripTrack.service.MemberService;
import com.springboot.tripTrack.serviceImpl.UserSecurityService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {
	private final UserSecurityService userService;
	private final MemberService memberService;
	private final FileService fService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/join.do")
	public ModelAndView putMember() {
		ModelAndView mav = new ModelAndView("join");
		return mav;
	}

	@PostMapping("/join.do")
	public ModelAndView putMember(MemberDto memberForm) {
		userService.create(memberForm);
		ModelAndView mav = new ModelAndView("login");

		return mav;
	}

	@GetMapping("/login.do")
	public String login() {
		return "login";
	}

	@GetMapping(path = "/update.do")
	public String updateMember(MemberDto member) throws Exception {

		member.setUser_pw(passwordEncoder.encode(member.getUser_pw()));
		memberService.updateMember(member);
		return "redirect:/";
	}

	@GetMapping(path = "/delete.do")
	public String deleteMember(@RequestParam("user_id") String user_id, HttpSession session) throws Exception {
		memberService.deleteMember(user_id);
		session.invalidate();
		return "redirect:/logout.do";
	}

	@GetMapping("/checkId.do")
	@ResponseBody
	public String checkId(@RequestParam("user_id") String user_id) throws Exception {
		String result = "N";

		int flag = memberService.checkId(user_id);

		if (flag == 1)
			result = "Y";
		// 아이디가 있을시 Y 없을시 N 으로jsp view 로 보냄
		return result;
	}

	@GetMapping("/checkEmail.do")
	@ResponseBody
	public String checkEmail(@RequestParam("email") String email) throws Exception {
		String result = "N";

		int flag = memberService.checkEmail(email);

		if (flag == 1) {
			result = "Y";
		}

		return result;
	}

	@GetMapping("/checkNickname.do")
	@ResponseBody
	public String checkNickname(@RequestParam("nickname") String nickname) throws Exception {
		String result = "N";

		int flag = memberService.checkNickname(nickname);

		if (flag == 1) {
			result = "Y";
		}
		// 아이디가 있을시 Y 없을시 N 으로jsp view 로 보냄
		return result;
	}

	@GetMapping("/checkPassword.do")
	@ResponseBody
	public String checkPassword(@RequestParam("user_pw1") String user_pw, @RequestParam("user_pw2") String user_pw2)
			throws Exception {

		// 가져온 비밀번호와 입력된 비밀번호가 일치하는지 확인합니다.
		if (user_pw == "" && user_pw2 == "") {
			return "Null"; // 비밀번호 일치
		}

		if (user_pw != "" && user_pw2 != "" && user_pw.equals(user_pw2)) {
			return "Y"; // 비밀번호 불일치
		} else {
			return "N";
		}

	}

	@GetMapping("/checkTel.do")
	@ResponseBody
	public String checkTel(@RequestParam("tel") String tel) throws Exception {
		if (tel.isEmpty()) {
			return "Null"; // 비밀번호 중 하나라도 비어 있는 경우
		}

		// 정규 표현식을 사용하여 전화번호 유효성을 검사합니다.
		String telPattern = "^01([0|1|6|7|8|9])(\\d{3,4})(\\d{4})$";

		if (tel.matches(telPattern)) {
			return "Y"; // 유효한 전화번호
		} else {
			return "N"; // 유효하지 않은 전화번호
		}
	}

	// 아이디 찾기
	@GetMapping("/findUsername.do")
	public String findUsernameForm() {
		return "findUsername"; // findUsername.html 파일로 이동
	}

	@PostMapping("/findUsername.do")
	public String findUsername(@RequestParam("email") String email, Model model) {
		// 이메일을 사용하여 아이디를 찾는 로직을 구현합니다.
		String username = userService.findUsernameByEmail(email);
		if (username != null) {
			model.addAttribute("user_id", username);
			return "findUsernameResult"; // 아이디 찾기 결과 페이지로 이동
		} else {
			model.addAttribute("error", "해당 이메일로 가입된 아이디가 없습니다.");
			return "findUsername";
		}
	}

	// 비밀번호 찾기
	@GetMapping("/findPassword.do")
	public String findPasswordForm() {
		return "findPassword"; // findPassword.html 파일로 이동
	}

	@PostMapping("/findPassword.do")
	public String findPassword(@RequestParam("username") String user_id, @RequestParam("email") String email,
			Model model) {
		// 아이디와 이메일을 사용하여 비밀번호를 찾는 로직을 구현합니다.
		boolean result = userService.resetPassword(user_id, email);
		if (result) {
			return "findPasswordResult"; // 비밀번호 초기화 결과 페이지로 이동
		} else {
			model.addAttribute("error", "해당 정보로 가입된 계정이 없습니다.");
			return "findPassword";
		}
	}
}
