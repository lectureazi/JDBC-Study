package com.mc.jdbc.rent.validator;

import java.sql.Connection;
import java.util.List;

import com.mc.jdbc.book.dao.BookDao;
import com.mc.jdbc.common.util.JDBCTemplate;
import com.mc.jdbc.common.validator.Errors;
import com.mc.jdbc.member.dao.MemberDao;
import com.mc.jdbc.rent.dao.RentDao;

public class RentFormValidator {
	
	private static final RentFormValidator INSTANCE = new RentFormValidator();
	
	private MemberDao memberDao = new MemberDao();
	private BookDao bookDao = new BookDao();
	private RentDao rentDao = new RentDao();
	private JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public static RentFormValidator getInstance() {
		return INSTANCE;
	}
	
	private RentFormValidator() {}
	
	// 데이터베이스에 사용자 ID와 대출할 도서들이 존재하는지 검증
	public Errors validateResistRent(String userId, List<Integer> bkIdxs){
		
		Connection conn = jdt.getConnection();
		
		Errors errors = new Errors();
		
		try {
			
			if(!memberDao.existMemberById(conn, userId)) {
				errors.addError("userId", userId + "는 존재하지 않는 사용자 아이디 입니다.");
			}
			
			for (int bkIdx : bkIdxs) {
				if(!bookDao.existBookByIdx(conn, bkIdx)) {
					errors.addError("bkIdx", bkIdx + "는 없는 도서번호 입니다.");
				}
			}
			
		} finally {
			jdt.close(conn);
		}
		
		return errors;
	}
	
	public Errors validateReturnRentBook(int rbIdx) {
		Errors errors = new Errors();
		Connection conn = jdt.getConnection();
		
		try {
			if(!rentDao.existRentBookByIdx(conn, rbIdx)) {
				errors.addError("rbIdx", rbIdx + "는 없는 대출도서 입니다.");
			}
		} finally {
			jdt.close(conn);
		}
		
		return errors;
	}
	
	
	
	
	
	
	
	
	
	

}
