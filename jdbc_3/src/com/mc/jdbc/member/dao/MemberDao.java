package com.mc.jdbc.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mc.jdbc.common.code.ErrorCode;
import com.mc.jdbc.common.exception.DataAccessException;
import com.mc.jdbc.common.exception.HandleableException;
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
					member = generateMember(rset);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return member;
	}

	private Member generateMember(ResultSet rset) throws SQLException {
		Member member;
		member = new Member();
		member.setUserId(rset.getString("user_id"));
		member.setPassword(rset.getString("password"));
		member.setGrade(rset.getString("grade"));
		member.setTell(rset.getString("tell"));
		member.setEmail(rset.getString("email"));
		member.setLeave(rset.getBoolean("is_leave"));
		member.setRegDate(rset.getTimestamp("reg_date").toLocalDateTime());
		member.setRentableDate(rset.getTimestamp("rentable_date").toLocalDateTime());
		return member;
	}

	public void insertMember(Connection conn, Member member) {
		
		String sql = "insert into member(user_id, password, email, grade, tell, rentable_date)"
				+ " values('"+ member.getUserId() +"',"
					+ "'" + member.getPassword() + "',"
					+ "'" + member.getEmail() + "',"
					+ "'" + member.getGrade() + "',"
					+ "'" + member.getTell() + "',"
					+ " now() "	
					+ ")";
		
		Statement stmt = null;
		
		try {
			
			stmt = conn.createStatement();
			int res = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		} finally {
			jdt.close(stmt);
		}
	}
	
	//userId의 비밀번호를
	//매개변수로 받아온 password로 수정하는 코드를 작성하시오. 
	public int changePassword(Connection conn, String userId, String password) {
		
		//' or 1=1 or user_Id = '
		
		Statement pstm = null;
		int res = 0;

		String sql = "update member set password = '"+ password +"' where user_id = '" + userId +"'";
		System.out.println(sql);
		try {
			
			pstm = conn.createStatement();
			res = pstm.executeUpdate(sql);
			
			// runnable Exception은 예외처리가 강제되지 않기 때문에, catch를 만날 때 까지 계속 호출부로 전달
			if(res > 1) throw new HandleableException(ErrorCode.OCCURS_ANORMALY_ABOUT_ROW_CNT);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
			
		} finally {
			jdt.close(pstm);
		}
		
		return res;
	}
	
	
	// 원하는 사용자의 아이디를 삭제하는 메서드
	public int deleteUser(Connection conn, String userId) {
		
		//' or 1=1 or user_Id = '
	
		Statement pstm = null;
		int res = 0;
		String sql = "delete from member where user_id = '" + userId + "'" ;
		
		try {
			
			System.out.println(sql);
			
			pstm = conn.createStatement();
			res = pstm.executeUpdate(sql);
			
			if(res > 1) {
				System.out.println(res);
				throw new HandleableException(ErrorCode.OCCURS_ANORMALY_ABOUT_ROW_CNT);
			}
			
		}  catch (SQLException e) {
			
			e.printStackTrace();
			throw new DataAccessException();
			
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}

	public List<Member> selectAllMember(Connection conn) {
		
		List<Member> members = new ArrayList<Member>();
		
		try {
			
			PreparedStatement pstm = conn.prepareStatement("select * from member");
			ResultSet rset = pstm.executeQuery();
			
			while(rset.next()) {
				members.add(generateMember(rset));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return members;
	}

	public Member selectMemberById(Connection conn, String userId) {
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		Member member = null;
		String sql = "select * from member where user_id = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				member = generateMember(rset);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(rset, pstm);
		}
		
		return member;
	}
	
	public boolean existMemberById(Connection conn, String userId) {
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		Member member = null;
		String sql = "select * from member where user_id = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				member = generateMember(rset);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(rset, pstm);
		}
		
		return member != null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
