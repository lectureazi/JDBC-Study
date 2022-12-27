package com.mc.jdbc.common.code;

public enum ErrorCode {
	
	OCCURS_ANORMALY_ABOUT_ROW_CNT("영향을 받은 행의 숫자가 비정상적입니다.");
	
	public final String MESSAGE;
	
	private ErrorCode(String message) {
		this.MESSAGE = message;
	}

}
