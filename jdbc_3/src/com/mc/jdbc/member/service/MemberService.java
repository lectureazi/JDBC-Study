package com.mc.jdbc.member.service;

import java.sql.Connection;
import java.util.List;

import com.mc.jdbc.common.exception.HandleableException;
import com.mc.jdbc.common.util.JDBCTemplate;
import com.mc.jdbc.member.dao.MemberDao;
import com.mc.jdbc.member.dto.Member;

// MVC2패턴
// Model
// Service

// 역할
// 비지니스 로직(기능 구현)을 구현
// DB transaction 관리
//		transaction : 논리적 최소 작업 단위.
//	    commit/rollback을 Service에서 결정

public class MemberService {
	
	private JDBCTemplate jdt = JDBCTemplate.getInstance();
	private MemberDao memberDao = new MemberDao();
	
	// 사용자인증에 대한 비지니스 로직
	// 사용자인증에 대한 트랜잭션을 시작하고 종료
	// Connection 객체의 생성과 종료를 담당, commit/rollback
	public Member userAuthenticate(String userId, String password) {
		
		Connection conn = jdt.getConnection();
		
		try {
			// DataAccessObject에게 사용자의 아이디와 password로 데이터를 조회할 것을 요청
			Member member = memberDao.userAuthenticate(conn, userId, password);
			return member;
		}finally {
			jdt.close(conn);
		}
	}

	public List<Member> selectAllMember() {
		
		Connection conn = jdt.getConnection();
		
		List<Member> members = null;
		
		try {
			members = memberDao.selectAllMember(conn);
		}finally {
			jdt.close(conn);
		}
		
		return members;
	}

	public boolean insertMember(Member member) {
		
		Connection conn = jdt.getConnection();
		
		try {
			
			member.setGrade("ROLE_USER");
			memberDao.insertMember(conn, member);
			jdt.commit(conn);
			return true;
			
		} catch(Exception e){
			//데이터베이스 관련 예외 : SQLException -> DataAccessException
			//그 외 어떤 예외가 발생하더라도 반드시 rollback 처리 해야하기 때문에 일단 Exception 으로 예외처리
			jdt.rollback(conn);
		}finally {
			jdt.close(conn);
		}
		
		return false;
		
	}

	public boolean updatePassword(Member member) {

		Connection conn = jdt.getConnection();
		
		try {
			
			int res = memberDao.changePassword(conn, member.getUserId(), member.getPassword());
			jdt.commit(conn);
			
			return res == 1;
			
		} catch (Exception e) {
			
			jdt.rollback(conn);
			throw e;
			
		} finally {
			jdt.close(conn);
		}
	}

	public boolean deleteUser(String userId) {
		
		Connection conn = jdt.getConnection();
		
		try {
			
			int res = memberDao.deleteUser(conn, userId);
			jdt.commit(conn);
			
			return res == 1;
			
		} catch (Exception e) {
			
			jdt.rollback(conn);
			throw e;
			
		} finally {
			jdt.close(conn);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
