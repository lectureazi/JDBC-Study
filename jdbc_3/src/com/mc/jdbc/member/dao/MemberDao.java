package com.mc.jdbc.member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mc.jdbc.common.util.JDBCTemplate;
import com.mc.jdbc.member.dto.Member;

// MVC2 패턴
// Model
// DAO (Data Access Object)

// Persistence Layer 
// 영속성 계층 : 데이터를 영구적으로 저장하기 위해 DB와 상호작용하는 Layer

// 필요한 데이터를 DBMS에 요청
// DBMS로 부터 읽어온 데이터를 어플리케이션 내에서 사용하기 적합한 형태로 파싱
public class MemberDao {
	
	private JDBCTemplate jdt = JDBCTemplate.getInstance();

	// 사용자 인증
	public Member userAuthenticate(Connection conn, String userId, String password) {

		Member member = null;
		String query = "select * from member where user_id = '" + userId + "' and password = '" + password + "'";
		
		try(Statement stmt = conn.createStatement()) {
			try(ResultSet rset = stmt.executeQuery(query)){
				
				while (rset.next()) {
					member = new Member();
					member.setUserId(rset.getString("user_id"));
					member.setPassword(rset.getString("password"));
					member.setGrade(rset.getString("grade"));
					member.setTell(rset.getString("tell"));
					member.setEmail(rset.getString("email"));
					member.setLeave(rset.getBoolean("is_leave"));
					member.setRegDate(rset.getTimestamp("reg_date").toLocalDateTime());
					member.setRentableDate(rset.getTimestamp("rentable_date").toLocalDateTime());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return member;
	}

	public int insertMember(Member member) {
		
		String sql = "insert into member(user_id, password, email, grade, tell, rentable_date)"
				+ " values('"+ member.getUserId() +"',"
					+ "'" + member.getPassword() + "',"
					+ "'" + member.getEmail() + "',"
					+ "'" + member.getGrade() + "',"
					+ "'" + member.getTell() + "',"
					+ " now() "	
					+ ")";
		
		int res = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = jdt.getConnection();
			stmt = conn.createStatement();
			res = stmt.executeUpdate(sql);
			
			jdt.commit(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			jdt.rollback(conn);
		} finally {
			jdt.close(stmt);
			jdt.close(conn);
		}
		
		return res;
	}
	
	//userId의 비밀번호를
	//매개변수로 받아온 password로 수정하는 코드를 작성하시오. 
	public int changePassword(String userId, String password) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		int res = 0;

		String sql = "update member set password = ? where user_id = ?" ;
		
		try {
			
			conn = jdt.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, password); 
			pstm.setString(2, userId);
			res = pstm.executeUpdate();
			
			jdt.commit(conn);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			jdt.rollback(conn);
			
		} finally {
			jdt.close(pstm);
			jdt.close(conn);
		}
		
		return res;
	}
	
	
	// 원하는 사용자의 아이디를 삭제하는 메서드
	public int deleteUser(String userId) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		int res = 0;
		String sql = "delete from member where user_id = ?" ;
		
		try {
			
			conn = jdt.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			res = pstm.executeUpdate();
			
			jdt.commit(conn);
			
		}  catch (SQLException e) {
			
			e.printStackTrace();
			jdt.rollback(conn);
			
		} finally {
			jdt.close(pstm);
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
