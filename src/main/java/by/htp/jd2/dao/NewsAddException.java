package by.htp.jd2.dao;

public class NewsAddException extends Exception {

	private static final long serialVersionUID = 1L;

	public NewsAddException() {
		super();
	}

	public NewsAddException(String message) {
		super(message);
	}

	public NewsAddException(String message, Exception exception) {
		super(message, exception);
	}

	public NewsAddException(Exception exception) {
		super(exception);
	}
}
