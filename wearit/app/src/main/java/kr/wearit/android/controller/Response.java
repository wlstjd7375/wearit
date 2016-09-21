package kr.wearit.android.controller;

public class Response<T> {

	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_FAIL = "fail";

	private String status;
	private T data;
	private String exception;
	private String message;

	protected Response(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public boolean isSuccess() {
		return STATUS_SUCCESS.equals(status);
	}

	public T getData() {
		return data;
	}

	public String getException() {
		return exception;
	}

	public String getMessage() {
		return message;
	}
}
