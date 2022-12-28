package com.mc.jdbc.common.code;

public enum RentState {
	
	RE00("RE00", "대출"),
	RE01("RE01", "연장"),
	RE02("RE02", "연체"),
	RE03("RE03", "반납");
	
	public final String CODE;
	public final String DESC;
	
	RentState(String CODE, String DESC) {
		this.CODE = CODE;
		this.DESC = DESC;
	}

}
