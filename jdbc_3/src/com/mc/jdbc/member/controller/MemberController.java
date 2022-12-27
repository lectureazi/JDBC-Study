package com.mc.jdbc.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mc.jdbc.member.dto.Member;
import com.mc.jdbc.member.service.MemberService;

// MVC2 패턴
// Model, View, Controller 로 소프트웨어 구조를 구성하는 소프트웨어아키텍처 패턴

// Controller

// Presentation Layer : Application에서 Client와 직접 상호작용하는 Layer
//						핵심 로직인 Service Layer가 외부의 변화에 종속되지 않도록 분리하기 위해
//					    Client와 직접 상호작용하는 Presentation Layer를 분리.
//						Client 변경시 Controller의 코드만 변경이 발생

// 1. 입력데이터를 어플리케이션 내에서 사용하기 적합한 형태로 파싱
// 2. 부적합한 요청에 대해 허가/불가를 처리하는 외벽 역할(validator, 권한관리)
// 3. Client에게 비즈니스로직의 결과물을 어떻게 보여줄 것 인지 선택
//	  HTMl로 보낼 것인지, JSON, XML 같은 특정 데이터형식으로 보낼 것인지 등등...

public class MemberController {
	
	private MemberService memberService = new MemberService();

	public Map<String, Object> login(String userId, String password) {
		
		Member member = memberService.userAuthenticate(userId, password);
		
		Map<String, Object> res = new HashMap<>();
		
		res.put("isSuccess", true);
		res.put("userInfo", member);
		
		if(member == null) res.put("isSuccess", false);
		return res;
	}

	public List<Member> searchAllMember() {
		
		List<Member> members = memberService.selectAllMember();
		return members;
	}

	public String join(Member member) {
		
		if(memberService.insertMember(member)) return "회원가입이 완료되었습니다.";
		return "회원가입에 실패하였습니다.";
		
	}

	public String changePassword(Member member) {
		
		if(memberService.updatePassword(member)) return "비밀번호 변경이 완료되었습니다.";
		return "존재하지 않는 아이디 입니다.";
		
	}

	public String deleteUser(String userId) {
		
		if(memberService.deleteUser(userId)) return "회원 삭제가 정상적으로 완료되었습니다.";
		return "존재하지 않는 아이디 입니다.";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
