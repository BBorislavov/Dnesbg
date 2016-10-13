package com.topnews.exceptions;

public class AlertException extends Exception {
	
	private static final long serialVersionUID = -1197631764764951137L;

	public AlertException() {
		super();
	}

	public AlertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AlertException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlertException(String message) {
		super(message);
	}

	public AlertException(Throwable cause) {
		super(cause);
	}

}
