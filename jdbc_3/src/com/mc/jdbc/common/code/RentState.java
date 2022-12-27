package com.mc.jdbc.common.code;

public enum RentState {
	
	RE00("대출"),
	RE01("연장"),
	RE02("연체"),
	RE03("반납");

	public final String DESC;
	
	RentState(String DESC) {
		this.DESC = DESC;
	}

}
