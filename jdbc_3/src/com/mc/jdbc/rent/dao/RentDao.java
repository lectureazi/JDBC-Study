package com.mc.jdbc.rent.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mc.jdbc.common.exception.DataAccessException;
import com.mc.jdbc.common.util.JDBCTemplate;
import com.mc.jdbc.rent.dto.Rent;
import com.mc.jdbc.rent.dto.RentBook;

public class RentDao {
	
	private JDBCTemplate jdt = JDBCTemplate.getInstance();
	

	public void insertRentMaster(Connection conn, Rent rent) {
		
		//`RM_IDX`, `USER_ID`, `REG_DATE`, `IS_RETURN`, `TITLE`, `RENT_BOOK_CNT`
		String sql = "insert into rent_master(user_id, title, rent_book_cnt) values(?, ?, ?)";
		PreparedStatement pstm =  null;
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, rent.getUserId());
			pstm.setString(2, rent.getTitle());
			pstm.setInt(3, rent.getRentBookCnt());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(pstm);
		}
	}
	

	public void insertRentBook(Connection conn, RentBook rentBook) {
		
		//`RB_IDX`, `RM_IDX`, `BK_IDX`, `REG_DATE`, `STATE`, `RETURN_DATE`, `EXTENSION_CNT`
		
		String sql = "insert into rent_book(rm_idx, bk_idx, return_date) values("+ getRentMasterLastIdx() + ",? ,?)";
		PreparedStatement pstm = null;
		
		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rentBook.getBkIdx());
			pstm.setTimestamp(2, Timestamp.valueOf(rentBook.getReturnDate()));
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(pstm);
		}
		
	}

	public void insertRentHistory(Connection conn, int bkIdx, String state) {
		
		String sql = "insert into rent_history(rm_idx, rb_idx, bk_idx, state)"
				+ " values( " + getRentMasterLastIdx() +", " + getRentBookLastIdx() + ",  ?,?) ";
		
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bkIdx);
			pstm.setString(2, state);
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(pstm);
		}
	}


	
	
	
	
	
	
	private String getRentMasterLastIdx() {
		String sql = "(select rm_idx from rent_master order by reg_date desc limit 1)";
		return sql;
	}
	
	private String getRentBookLastIdx() {
		String sql = "(select rb_idx from rent_book order by reg_date desc limit 1)";
		return sql;
	}

	
}
