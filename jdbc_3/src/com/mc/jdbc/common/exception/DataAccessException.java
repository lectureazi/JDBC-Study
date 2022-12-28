package com.mc.jdbc.common.exception;

public class DataAccessException extends HandleableException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5077136424504019380L;

	public DataAccessException() {
		super("데이터베이스 통신 중 장애가 발생하였습니다.");
	}
	
	
	
}
