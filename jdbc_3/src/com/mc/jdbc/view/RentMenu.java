package com.mc.jdbc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mc.jdbc.book.controller.BookController;
import com.mc.jdbc.common.code.RentState;
import com.mc.jdbc.rent.controller.RentController;
import com.mc.jdbc.rent.dto.RentBook;

public class RentMenu {
	
	Scanner sc = new Scanner(System.in);
	BookController bookController = new BookController();
	RentController rentController = new RentController();
	
	public void rentMenu() {
		
		do {
			
			try {
				System.out.println("\n*** 대출 관리 ***");
				System.out.println("1. 도서 대출");
				System.out.println("2. 도서 반납");
				System.out.println("3. 도서 연장");
				System.out.println("4. 대출 중인 대출건 조회");
				System.out.println("5. 끝내기");
				System.out.print("선택 : ");
				
				switch (sc.nextInt()) {
				case 1: 
					System.out.print("대출자의 아이디를 입력하세요 : ");
					String userId = sc.next();
					
					List<String> bkIdxs = new ArrayList<>();
					
					while(true) {
						System.out.print("대출할 도서의 도서번호를 입력하세요 :");
						bkIdxs.add(sc.next());
						
						System.out.print("대출할 도서가 더 존재하나요?(y/n) : ");
						
						if(sc.next().toUpperCase().equals("N")) {
							break;
						}
					}
					
					System.out.println(rentController.resistRent(userId, bkIdxs));
					break;
					
				case 2: //반납할 대출도서번호(rbIdx)를 입력받아
						//해당 rbIdx의 대출도서를 반납처리
					
					System.out.print("반납할 대출도서번호 : ");
					String rbIdx = sc.next();
					
					System.out.println(rentController.returnRentBook(rbIdx));
					
					break;
					
				case 3: //연장할 대출도서번호(rbIdx)를 입력받아
						//해당 rbIdx의 대출도서를 연장처리 
					
						//대출 도서를 연장할 경우, 대출도서의 state를 RE01 수정
						//대출 도서의 반납일자를 반납일로부터 7일을 추가
						//대출히스토리 테이블에 대출도서가 연장 상태로 변경되었음을 저장
					break;
					
				case 4: //대출건을 조회할 rm_idx를 입력받아
						//rentController 의 searchRentBookWithRmIdx 메서드 호출
						//해당 대출건에 대한 상세정보를 출력
						System.out.print("조회할 대출번호를 입력하세요 : ");
						String rmIdx = sc.next();
						List<RentBook> rentBooks = rentController.searchRentBookWithRmIdx(rmIdx);
						for (RentBook rentBook : rentBooks) {
							System.out.println(
									"대출번호 : " 			+ rentBook.getRmIdx() 
									+ ", 대출도서번호 : " 	+ rentBook.getRbIdx()
									+ ", 대출 상태 : " 		+ RentState.valueOf(rentBook.getState()).DESC);
						}
					 
					break;
				case 5: return;
				
				default:System.out.println("잘못된 숫자를 입력하셨습니다.");

				}
				
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
			
			
			
		}while(true);
	}
	
	
	
	
	
	
	
	
	
}
