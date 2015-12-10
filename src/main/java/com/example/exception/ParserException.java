package com.example.exception;

public class ParserException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public static final int GENERAL_ERROR = 0;
	public static final int TAG_ERROR = 1;
	public static final int REQUEST_ERROR = 2;
	
	//variable used to distinguish type of exception 
	private int code = GENERAL_ERROR;

	public ParserException() {
        super();
    }

    public ParserException(Throwable cause) {
        super(cause);
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ParserException(int code, String message) {
		super(message);
		this.code = code; 
	}
	
    public ParserException(int code, String message, Throwable cause) {
    	super(message, cause);
    	this.code = code; 
    }
    
    public ParserException(int code, Throwable cause) {
    	super(cause);
    	this.code = code; 
    }
    
    public ParserException(int code) {
    	super();
    	this.code = code; 
    }
    
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
