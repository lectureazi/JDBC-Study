package com.mc.jdbc.rent.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mc.jdbc.common.code.RentState;
import com.mc.jdbc.common.exception.DataAccessException;
import com.mc.jdbc.common.util.JDBCTemplate;
import com.mc.jdbc.rent.dto.Rent;
import com.mc.jdbc.rent.dto.RentBook;

public class RentDao {
	
	private JDBCTemplate jdt = JDBCTemplate.getInstance();
	

	private String getRentMasterLastIdx() {
		String sql = "(select rm_idx from rent_master order by rm_idx desc limit 1)";
		return sql;
	}
	
	private String getRentBookLastIdx() {
		String sql = "(select rb_idx from rent_book order by rb_idx desc limit 1)";
		return sql;
	}
	
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

	public void insertRentHistoryWhenNewRent(Connection conn, int bkIdx, String state) {
		
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



	public boolean existRentBookByIdx(Connection conn, int rbIdx) {
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String sql = "select * from rent_book where rb_idx = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rbIdx);
			rset = pstm.executeQuery();
			return rset.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		} finally {
			jdt.close(rset, pstm);
		}
	}

	public void updateRentBookState(Connection conn, int rbIdx, RentState re) {
		
		String sql = "update rent_book set state = ? where rb_idx = ?";
		
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, re.CODE);
			pstm.setInt(2, rbIdx);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(pstm);
		}
	}

	public void updateRentBookReturnDate(Connection conn, int rbIdx) {
		
		String sql = "update rent_book set return_date = now() where rb_idx = ?";
		PreparedStatement pstm = null;
		
		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rbIdx);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(pstm);
		}
	}

	public void insertRentHistoryWhenChangeRent(Connection conn, int rbIdx, RentState re) {
		
		String rmIdx = "(select rm_idx from rent_book where rb_idx = "+ rbIdx + ")";
		String bkIdx = "(select bk_idx from rent_book where rb_idx = "+ rbIdx + ")";
		
		String sql = "insert into rent_history(rm_idx, rb_idx, bk_idx, state) "
				+ "values(" + rmIdx 
				+", ?,"  
				+ bkIdx
				+", ? )";
		
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rbIdx);
			pstm.setString(2, re.CODE);
			pstm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(pstm);
		}
	}

	public RentBook selectRentBookByIdx(Connection conn, int rbIdx) {
		
		String sql = "select * from rent_book where rb_idx = ?";
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		RentBook rentBook = null;
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rbIdx);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				rentBook = generateRentBook(rset);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(rset, pstm);
		}
		
		return rentBook;
	}

	private RentBook generateRentBook(ResultSet rset) throws SQLException {
		RentBook rentBook;
		rentBook = new RentBook();
		rentBook.setBkIdx(rset.getInt("bk_idx"));
		rentBook.setExtenstionCnt(rset.getInt("extension_cnt"));
		rentBook.setRbIdx(rset.getInt("rb_idx"));
		rentBook.setRegDate(rset.getTimestamp("reg_date").toLocalDateTime());
		rentBook.setReturnDate(rset.getTimestamp("return_date").toLocalDateTime());
		rentBook.setRmIdx(rset.getInt("rm_idx"));
		rentBook.setState(rset.getString("state"));
		return rentBook;
	}

	private Rent genrateRent(ResultSet rset) throws SQLException {
		Rent rent;
		rent = new Rent();
		rent.setRegDate(rset.getTimestamp("reg_date").toLocalDateTime());
		rent.setRentBookCnt(rset.getInt("rent_book_cnt"));
		rent.setReturn(rset.getBoolean("is_return"));
		rent.setRmIdx(rset.getInt("rm_idx"));
		rent.setTitle(rset.getString("title"));
		rent.setUserId(rset.getString("user_id"));
		
		return rent;
	}

	public List<RentBook> selectAllRentBookByRmIdx(Connection conn, int rmIdx) {
		
		String sql = "select * from rent_book where rm_idx = ?";
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		List<RentBook> rentBooks = new ArrayList<RentBook>();
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rmIdx);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				rentBooks.add(generateRentBook(rset));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(rset, pstm);
		}
		
		
		return rentBooks;
	}

	public void updateRentToReturn(Connection conn, int rmIdx) {

		String sql = "update rent_master set is_return = true where rm_idx = ?";
		
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rmIdx);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(pstm);
		}
	}

	public List<RentBook> selectRentBookWithRmIdx(Connection conn, int rmIdx) {

		String sql = "select * from rent_book where rm_idx = ?";
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		List<RentBook> rentBooks = new ArrayList<RentBook>();
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rmIdx);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				rentBooks.add(generateRentBook(rset));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}finally {
			jdt.close(rset, pstm);
		}
		
		return rentBooks;
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
}
