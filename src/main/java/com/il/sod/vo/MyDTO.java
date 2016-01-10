package com.il.sod.vo;

public class MyDTO {
	private String val1 = "";
	private int val2 = 0;
	public MyDTO(){}
	public MyDTO(String val1, int val2){
		super();
		this.val1 = val1;
		this.val2 = val2;
	}

	public String getVal1() {
		return val1;
	}
	public void setVal1(String val1) {
		this.val1 = val1;
	}
	public int getVal2() {
		return val2;
	}
	public void setVal2(int val2) {
		this.val2 = val2;
	}
}
