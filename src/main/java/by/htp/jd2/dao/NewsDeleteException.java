package by.htp.jd2.dao;

public class NewsDeleteException extends Exception {

	private static final long serialVersionUID = 1L;

	public NewsDeleteException() {
		super();
	}

	public NewsDeleteException(String message) {
		super(message);
	}

	public NewsDeleteException(String message, Exception exception) {
		super(message, exception);
	}

	public NewsDeleteException(Exception exception) {
		super(exception);
	}
}
