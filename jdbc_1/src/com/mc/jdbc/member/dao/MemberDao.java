package com.mc.jdbc.member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mc.jdbc.member.dto.Member;

// MVC2 패턴
// Model
// DAO (Data Access Object)

// Persistence Layer 
// 영속성 계층 : 데이터를 영구적으로 저장하기 위해 DB와 상호작용하는 Layer

// 필요한 데이터를 DBMS에 요청
// DBMS로 부터 읽어온 데이터를 어플리케이션 내에서 사용하기 적합한 형태로 파싱
public class MemberDao {
	
	
	

	// 사용자 인증
	public Member userAuthenticate(String userId, String password) {

		Member member = null;

		// DBMS와의 연결(session)을 관리
		// transaction 관리를 위한 메서드를 가진 객체
		// transaction 시작 시 Connection 객체 생성
		// transaction 종료 시 Connection을 닫음 (close())
		Connection conn = null;

		// 쿼리 전송용 객체
		// select, insert, update, delete 같은 구문을 수행하기 위한 객체
		// 데이터베이스에 전달할 sql문을 작성
		Statement stmt = null;

		// Select 쿼리의 결과로 반환된 데이터들의 집합
		// DBMS로 부터 조회된 데이터를 읽어오는 객체
		ResultSet rset = null;

		try {
			// JDBC 코딩 순서
			// 1. jdbc driver 객체를 jvm에 등록
			// Reflection을 사용해 원하는 DBMS의 드라이버 객체를 JVM에 등록할 수 있다.
			// 인스턴스 생성을 문자열로 하고, 메서드나 필드에 접근할 수 있는 기술
			// private 같은 객체지향프로그래밍의 원칙을 위해 만든 자바의 규칙을 무시하고 메서드나 속성 등을 사용할 수
			// 있어서 가능하면 사용하지 않는 것이 좋다.

			// 우리가 사용할 DBMS의 Driver 클래스를 JVM에 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 데이터베이스와 연결
			// jdbc:mysql//<ip>:<port>/<database이름>?queryString
			String url = "jdbc:mysql://localhost:3306/bookmanager?useUnicode=true&characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "bm", "123qwe!@#QWE");

			// 3. 쿼리 전송용 객체 생성
			stmt = conn.createStatement();

			// 3-1. 쿼리 작성
			String query = "select * from member where user_id = '" + userId + "' and password = '" + password + "'";

			rset = stmt.executeQuery(query);

			// resultSet에서 각 row를 한 줄씩 읽어오는 코드
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return member;
	}

	public int insertMember(Member member) {
		
		Connection conn = null;
		Statement stmt = null;
		int res = 0;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/bookmanager?useUnicode=true&characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "bm", "123qwe!@#QWE");
			
			// 3. 쿼리 전송용 객체 생성
			stmt = conn.createStatement();
			
			//`USER_ID`, `PASSWORD`, `EMAIL`, `GRADE`, `TELL`, `IS_LEAVE`, `REG_DATE`, `RENTABLE_DATE`
			String sql = "insert into member(user_id, password, email, grade, tell, rentable_date)"
					+ " values('"+ member.getUserId() +"',"
						+ "'" + member.getPassword() + "',"
						+ "'" + member.getEmail() + "',"
						+ "'" + member.getGrade() + "',"
						+ "'" + member.getTell() + "',"
						+ " now() "	
						+ ")";
			
			res = stmt.executeUpdate(sql);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	//userId의 비밀번호를
	//매개변수로 받아온 password로 수정하는 코드를 작성하시오. 
	public int changePassword(String userId, String password) {
		
		Connection conn = null;
		
		// SQL Injecton 공격을 방어할 수 있는 쿼리 전송용 객체
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/bookmanager?useUnicode=true&characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "bm", "123qwe!@#QWE");
			
			//`USER_ID`, `PASSWORD`, `EMAIL`, `GRADE`, `TELL`, `IS_LEAVE`, `REG_DATE`, `RENTABLE_DATE`
			
			// PreparedStatement용 쿼리 템플릿
			// ? 에 담길 데이터가 문자열일 경우 미리 이스케이프처리 해준다.
			// 문자열을 특수한 형태로 변환. 입력된 문자열 데이터의 앞 뒤에 ' 을 붙여준다.
			// a -> 'a'
			
			String sql = "update member set password = ? where user_id = ?" ;
			System.out.println(sql);

			// 3. 쿼리 전송용 객체 생성
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, password);  // 첫번 째 물음표에 password 값을 이스케이프처리하여 넣어줌
			pstm.setString(2, userId);
			res = pstm.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	
	// 원하는 사용자의 아이디를 삭제하는 메서드
	public int deleteUser(String userId) {
		
		Connection conn = null;
		
		// SQL Injecton 공격을 방어할 수 있는 쿼리 전송용 객체
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/bookmanager?useUnicode=true&characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "bm", "123qwe!@#QWE");
			
			//`USER_ID`, `PASSWORD`, `EMAIL`, `GRADE`, `TELL`, `IS_LEAVE`, `REG_DATE`, `RENTABLE_DATE`
			
			// PreparedStatement용 쿼리 템플릿
			// ? 에 담길 데이터가 문자열일 경우 미리 이스케이프처리 해준다.
			// 문자열을 특수한 형태로 변환. 입력된 문자열 데이터의 앞 뒤에 ' 을 붙여준다.
			// a -> 'a'
			
			String sql = "delete from member where user_id = ?" ;

			// 3. 쿼리 전송용 객체 생성
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			res = pstm.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
