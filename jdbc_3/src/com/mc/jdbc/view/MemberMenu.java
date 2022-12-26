package com.mc.jdbc.view;

import java.util.Scanner;

import com.mc.jdbc.member.controller.MemberController;
import com.mc.jdbc.member.dto.Member;

// MVC2  
// view
// UI

public class MemberMenu {
	
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();
	
	public void memberMenu() {
		
		do {
			System.out.println("\n***  회원 관리 프로그램 ***");
			System.out.println("1. 회원 전체 조회");
			System.out.println("2. 새 회원 등록");
			System.out.println("3. 회원 암호 수정");
			System.out.println("4. 회원 탈퇴");
			System.out.println("5. 종료");
			System.out.print("번호 입력 : ");
			
			switch(sc.nextInt()) {
				case 1: 
				
					break;
				case 2: 
				
					break;
				case 3: 
					sc.nextLine();
					Member member = new Member();
					System.out.print("비밀번호를 변경할 아이디 : ");
					member.setUserId(sc.nextLine());
					
					System.out.print("변경할 비밀번호 : ");
					member.setPassword(sc.next());
					
					break;
					
				case 4: 
					System.out.print("탈퇴 시킬 회원의 아이디 입력 : ");
					String userId = sc.next();
														
					break;
									
				case 5: return;
				default : System.out.println("잘못 입력하셨습니다. 다시 입력하세요.");
			}
		}while(true);
	}
	
		
	//사용자로부터 회원가입 정보를 받아서 member객체로 반환
	public Member join() {
		
		Member member = new Member();
		
		System.out.println("회원 정보를 입력하세요.-------------");
		
		System.out.print("아이디 : ");
		member.setUserId(sc.next());
		
		System.out.print("암호 : ");
		member.setPassword(sc.next());
		
		System.out.print("이메일 : ");
		member.setEmail(sc.next());
		
		System.out.print("전화 번호 : ");
		member.setTell(sc.next());
		return member;
	}
	

}
