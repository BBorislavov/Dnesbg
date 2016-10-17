package com.topnews.exceptions;

public class NameException extends Exception {

	private static final long serialVersionUID = -7720552242399785577L;

	public NameException() {
		super();
	}

	public NameException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public NameException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NameException(String arg0) {
		super(arg0);
	}

	public NameException(Throwable arg0) {
		super(arg0);
	}
}
