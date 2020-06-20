package com.zhaoyujie.util;

/**
 * 自定义 exception 类
 * 
 * @author luoqilin
 *
 */
public class MyException extends Exception {

	private static final long serialVersionUID = 1L;

	private String reason; // 错误原因

	private ExceptionType type; // 错误类型

	public MyException(ExceptionType type) {
		super();
		this.type = type;
	}
	
	public MyException(String reason) {
		super();
		this.reason = reason;
	}

	public MyException(ExceptionType type, String reason) {
		super(reason);
		this.reason = reason;
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public ExceptionType getType() {
		return type;
	}

	public void setType(ExceptionType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MyException [type=" + type + ", reason=" + reason + "]";
	}

}