package com.mc.jdbc.rent.dto;

import java.time.LocalDateTime;

public class RentHistory {
	
	// `RH_IDX`, `RM_IDX`, `RB_IDX`, `BK_IDX`, `REG_DATE`, `STATE`
	private int rhIdx;
	private int rmIdx;
	private int rbIdx;
	private int bkIdx;
	private LocalDateTime regDate;
	private String state;
	
	
	public int getRhIdx() {
		return rhIdx;
	}
	
	public void setRhIdx(int rhIdx) {
		this.rhIdx = rhIdx;
	}
	
	public int getRmIdx() {
		return rmIdx;
	}
	public void setRmIdx(int rmIdx) {
		this.rmIdx = rmIdx;
	}
	public int getRbIdx() {
		return rbIdx;
	}
	public void setRbIdx(int rbIdx) {
		this.rbIdx = rbIdx;
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
	@Override
	public String toString() {
		return "RentHistory [rhIdx=" + rhIdx + ", rmIdx=" + rmIdx + ", rbIdx=" + rbIdx + ", bkIdx=" + bkIdx
				+ ", regDate=" + regDate + ", state=" + state + "]";
	}
	
	
	

}
