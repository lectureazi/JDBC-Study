package com.mc.jdbc;

import com.mc.jdbc.member.dao.MemberDao;
import com.mc.jdbc.member.dto.Member;

public class Run {
	
	// JDBC(Java Database Connectivity) API(Application Programing Interface)
	// Java Database Connectivity Application Programing Interface
	
	// 모든 데이터베이스는 자신의 제품과 자바 어플리케이션간의 연결을 위해 JDBC API를 구현체를 제공. 
	// 모든 데이터베이스가 JDBC API를 따르고 있기 때문에, 자바 개발자는 모든 데이터베이스를 공통된 형식으로 다룰 수 있다.
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MemberDao memberDao = new MemberDao();
		Member member = memberDao.userAuthenticate("admin", "1234");
		System.out.println(member);
		
		/////////////////////////////////////////////////////////////////
		
		Member member2 = new Member();
		member2.setUserId("super");
		member2.setPassword("1234");
		member2.setEmail("super@mc.com");
		member2.setGrade("ROLE_ADMIN");
		member2.setTell("010-0000-1112");
		
		int res = memberDao.insertMember(member2);
		System.out.println(res);
		
		
		// SQL injection 공격
		// 데이터를 오염시키기 위한 목적으로 SQL쿼리를 주입하는 공격
		//int res = memberDao.changePassword("super", "1234");
		//System.out.println(res);
		
		//int res = memberDao.deleteUser("super");
		//System.out.println(res);
		
		
		
		
		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
