package com.capgemini.library;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -3813525855564703877L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
