package exceptions;

public class NewsException extends Exception {

	private static final long serialVersionUID = 1999985966604541331L;

	public NewsException() {
		super();
	}

	public NewsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NewsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NewsException(String message) {
		super(message);
	}

	public NewsException(Throwable cause) {
		super(cause);
	}

}
