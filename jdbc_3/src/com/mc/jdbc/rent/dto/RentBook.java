package com.mc.jdbc.rent.dto;

import java.time.LocalDateTime;

public class RentBook {

	// `RB_IDX`, `RM_IDX`, `BK_IDX`, `REG_DATE`, `STATE`, `RETURN_DATE`, `EXTENSION_CNT`
	private int rbIdx;
	private int rmIdx;
	private int bkIdx;
	private LocalDateTime regDate;
	private String state;
	private LocalDateTime returnDate;
	private int extenstionCnt; // 연장 횟수
	
	public int getRbIdx() {
		return rbIdx;
	}
	
	public void setRbIdx(int rbIdx) {
		this.rbIdx = rbIdx;
	}
	
	public int getRmIdx() {
		return rmIdx;
	}
	
	public void setRmIdx(int rmIdx) {
		this.rmIdx = rmIdx;
	}
	
	public int getBkIdx() {
		return bkIdx;
	}
	
	public void setBkIdx(int bkIdx) {
		this.bkIdx = bkIdx;
	}
	
	public LocalDateTime getRegDate() {
		return regDate;
	}
	
	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public LocalDateTime getReturnDate() {
		return returnDate;
	}
	
	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}
	
	public int getExtenstionCnt() {
		return extenstionCnt;
	}
	
	public void setExtenstionCnt(int extenstionCnt) {
		this.extenstionCnt = extenstionCnt;
	}
	
	@Override
	public String toString() {
		return "RentBook [rbIdx=" + rbIdx + ", rmIdx=" + rmIdx + ", bkIdx=" + bkIdx + ", regDate=" + regDate
				+ ", state=" + state + ", returnDate=" + returnDate + ", extenstionCnt=" + extenstionCnt + "]";
	}
	
	
	
	
	
}
