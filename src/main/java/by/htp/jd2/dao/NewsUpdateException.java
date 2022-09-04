package by.htp.jd2.dao;

public class NewsUpdateException extends Exception {

	private static final long serialVersionUID = 1L;

	public NewsUpdateException() {
		super();
	}

	public NewsUpdateException(String message) {
		super(message);
	}

	public NewsUpdateException(String message, Exception exception) {
		super(message, exception);
	}

	public NewsUpdateException(Exception exception) {
		super(exception);
	}
}
