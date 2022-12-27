package com.mc.jdbc.view;

import java.util.Map;
import java.util.Scanner;

import com.mc.jdbc.member.controller.MemberController;

public class Index {
	
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();
	private MemberMenu memberMenu = new MemberMenu();

	
	public void startMenu() {
		
//		System.out.println("로그인 하세요.");
//		
//		System.out.print("아이디 : ");
//		String userId = sc.next();
//		
//		System.out.print("암호 : ");
//		String password = sc.next();
//
//		Map<String, Object> auth = memberController.login(userId, password);
//
//		Boolean isSuccess = (Boolean) auth.get("isSuccess");
		
		if(true) {
			while(true) {
				System.out.println("\n관리할 메뉴를 선택하세요.");
				System.out.println("1. 회원관리");
				System.out.println("2. 도서관리");
				System.out.println("3. 대출관리");
				System.out.println("4. 종료");
				System.out.print("입력 : ");
				
				switch(sc.nextInt()) {
				case 1 : memberMenu.memberMenu(); break;
				case 2 : new BookMenu().bookMenu(); break;
				case 3 : new RentMenu().rentMenu(); break;
				case 4 : return;
				default :  System.out.println("잘못된 숫자를 입력하셨습니다.");
				
				}
			}
		}else {
			System.out.println("아이디나 암호를 확인하세요.");
		}
	}

}
