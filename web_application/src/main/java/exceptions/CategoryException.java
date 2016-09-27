package exceptions;

public class CategoryException extends Exception {

	private static final long serialVersionUID = 4661257285897648827L;

	public CategoryException() {
		super();
	}

	public CategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CategoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategoryException(String message) {
		super(message);
	}

	public CategoryException(Throwable cause) {
		super(cause);
	}
}
