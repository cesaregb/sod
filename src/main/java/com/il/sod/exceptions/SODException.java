package com.il.sod.exceptions;

public class SODException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public static final int GENERAL_ERROR = 0;
	public static final int TAG_ERROR = 1;
	public static final int REQUEST_ERROR = 2;
	
	//variable used to distinguish type of exception 
	private int code = GENERAL_ERROR;

	public SODException() {
        super();
    }

    public SODException(Throwable cause) {
        super(cause);
    }

    public SODException(String message) {
        super(message);
    }

    public SODException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SODException(int code, String message) {
		super(message);
		this.code = code; 
	}
	
    public SODException(int code, String message, Throwable cause) {
    	super(message, cause);
    	this.code = code; 
    }
    
    public SODException(int code, Throwable cause) {
    	super(cause);
    	this.code = code; 
    }
    
    public SODException(int code) {
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
