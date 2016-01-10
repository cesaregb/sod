package com.il.sod.vo;

public class ErrorVo {
	public static int GENERAL_ERROR = 0;
	
	private int internalCode = 0; 
	private String message;
	
	public ErrorVo(int code, String message){
		this.internalCode = code; 
		this.message = message;
	}
	
	public int getInternalCode() {
		return internalCode;
	}
	public void setInternalCode(int internalCode) {
		this.internalCode = internalCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
