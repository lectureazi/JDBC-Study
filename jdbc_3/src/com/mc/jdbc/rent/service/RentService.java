package com.mc.jdbc.rent.service;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import com.mc.jdbc.book.dao.BookDao;
import com.mc.jdbc.book.dto.Book;
import com.mc.jdbc.common.util.JDBCTemplate;
import com.mc.jdbc.rent.dao.RentDao;
import com.mc.jdbc.rent.dto.Rent;
import com.mc.jdbc.rent.dto.RentBook;

public class RentService {
	
	private JDBCTemplate jdt = JDBCTemplate.getInstance();
	private RentDao rentDao = new RentDao();
	private BookDao bookDao = new BookDao();

	public Rent insertRent(String userId, List<Integer> bkIdxs) {
		
		Connection conn = jdt.getConnection();
		
		Rent rent = null;
		
		try {
			
			// 1. RentMaster 테이블에 대출에 대한 정보를 입력
			// 사용자ID, 대출제목, 대출도서권수
			// 대출제목 : 첫번째 빌린 대출도서명 + [외 n권]
			Book book = bookDao.selectBookByIdx(conn, bkIdxs.get(0));
			String title = book.getTitle() + "외 " + (bkIdxs.size() -1) + "권";
			rent = new Rent();
			rent.setTitle(title);
			rent.setUserId(userId);
			rent.setRentBookCnt(bkIdxs.size());
			
			rentDao.insertRentMaster(conn, rent);
						
			// 2. RentBook 테이블에 사용자가 대출한 개별 도서에 대한 정보를 입력
			// RM_IDX, 도서번호, 반납일자:오늘로부터 7일 뒤
			
			LocalDateTime returnDate = LocalDateTime.now().plusDays(7); 
			RentBook rentBook = null;
			
			for (int bkIdx : bkIdxs) {
				rentBook = new RentBook();
				rentBook.setBkIdx(bkIdx);
				rentBook.setReturnDate(returnDate);
				rentDao.insertRentBook(conn, rentBook);
				
				// 3. RentHistory 테이블에 각 대출도서별로 대출도서 상태에 대한 정보를 입력
				// RM_IDX, RB_IDX, BK_IDX
				rentDao.insertRentHistory(conn, bkIdx, "RE00");
			}
			
			jdt.commit(conn);
			
			
		}catch (Exception e) {
			jdt.rollback(conn);
			throw e;
		}finally {
			jdt.close(conn);
		}
		
		return rent;
		
	}
	
	
	
	
	
	
	

}
