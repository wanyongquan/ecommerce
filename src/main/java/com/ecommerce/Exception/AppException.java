/**
 * 统一的业务异常
 */
package com.ecommerce.Exception;

/**
 * 
 */
public class AppException extends RuntimeException{
	private final int status;
    private final String userMessage;
    private int errorCode =0;

    public AppException(int status, String userMessage, int errorCode, Throwable cause) {
        super(cause);
		this.status = status;
       
        this.userMessage = userMessage;
        this.errorCode = errorCode;
    }
    public AppException(String userMessage, Throwable cause) {
        super(cause);
		this.status = 0;
       
        this.userMessage = userMessage;
     
    }
    public AppException(int status, String userMessage) {
        super(userMessage);
		this.status = status;
       
        this.userMessage = userMessage;
     
    }
    public AppException(String userMessage, int errorCode, Throwable cause) {
        super(cause);
		this.status = 0;
       
        this.userMessage = userMessage;
        this.errorCode = errorCode;
    }
    public AppException(String userMessage) {
        super(userMessage);
		this.status = 0;
		
        this.userMessage = userMessage;
    }
    
    public AppException(String userMessage, int errorCode) {
        super(userMessage);
		this.status = 0;
		
        this.userMessage = userMessage;
        this.errorCode = errorCode;
    }
    

    public String getUserMessage() {
        return userMessage;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public int getErrorStatus() {
        return status;
    }
}
