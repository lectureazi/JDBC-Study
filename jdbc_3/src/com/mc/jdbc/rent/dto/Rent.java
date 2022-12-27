package com.mc.jdbc.rent.dto;

import java.time.LocalDateTime;

public class Rent {
	
	// `RM_IDX`, `USER_ID`, `REG_DATE`, `IS_RETURN`, `TITLE`, `RENT_BOOK_CNT`
	private int rmIdx;
	private String userId;
	private LocalDateTime regDate;
	private boolean isReturn;
	private String title;
	private int rentBookCnt;
	
	public int getRmIdx() {
		return rmIdx;
	}
	public void setRmIdx(int rmIdx) {
		this.rmIdx = rmIdx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDateTime getRegDate() {
		return regDate;
	}
	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}
	public boolean isReturn() {
		return isReturn;
	}
	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRentBookCnt() {
		return rentBookCnt;
	}
	public void setRentBookCnt(int rentBookCnt) {
		this.rentBookCnt = rentBookCnt;
	}
	@Override
	public String toString() {
		return "Rent [rmIdx=" + rmIdx + ", userId=" + userId + ", regDate=" + regDate + ", isReturn=" + isReturn
				+ ", title=" + title + ", rentBookCnt=" + rentBookCnt + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
