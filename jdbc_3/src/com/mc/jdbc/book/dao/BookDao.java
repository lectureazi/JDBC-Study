package com.mc.jdbc.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mc.jdbc.book.dto.Book;
import com.mc.jdbc.common.util.JDBCTemplate;

public class BookDao {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();

	public Book selectBookByIdx(Connection conn, int bkIdx) {
		
		String sql = "select * from book where bk_idx = ?";
		Book book = null;
		ResultSet rset = null;
		PreparedStatement pstm = null;
		
		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bkIdx);
			rset = pstm.executeQuery();
			
			//`BK_IDX`, `ISBN`, `CATEGORY`, `TITLE`, `AUTHOR`, `INFO`, `BOOK_AMT`, `REG_DATE`, `RENT_CNT`
			while(rset.next()) {
				book = generateBook(rset);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			jdt.close(rset, pstm);
		}

		return book;
	}
	
	public boolean existBookByIdx(Connection conn, int bkIdx) {
		
		String sql = "select * from book where bk_idx = ?";
		Book book = null;
		ResultSet rset = null;
		PreparedStatement pstm = null;
		
		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bkIdx);
			rset = pstm.executeQuery();
			
			//`BK_IDX`, `ISBN`, `CATEGORY`, `TITLE`, `AUTHOR`, `INFO`, `BOOK_AMT`, `REG_DATE`, `RENT_CNT`
			while(rset.next()) {
				book = generateBook(rset);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			jdt.close(rset, pstm);
		}

		return book != null;
	}

	private Book generateBook(ResultSet rset) throws SQLException {
		Book book;
		book = new Book();
		book.setAuthor(rset.getString("author"));
		book.setBkIdx(rset.getInt("BK_IDX"));
		book.setBookAmt(rset.getInt("BOOK_AMT"));
		book.setCategory(rset.getString("CATEGORY"));
		book.setInfo(rset.getString("INFO"));
		book.setIsbn(rset.getString("ISBN"));
		book.setRegDate(rset.getTimestamp("REG_DATE").toLocalDateTime());
		book.setRentCnt(rset.getInt("RENT_CNT"));
		book.setTitle(rset.getString("TITLE"));
		return book;
	}

}
