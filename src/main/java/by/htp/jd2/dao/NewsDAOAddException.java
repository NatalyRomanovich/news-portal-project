package by.htp.jd2.dao;

public class NewsDAOAddException extends Exception {

	private static final long serialVersionUID = 1L;

	public NewsDAOAddException() {
		super();
	}

	public NewsDAOAddException(String message) {
		super(message);
	}

	public NewsDAOAddException(String message, Exception exception) {
		super(message, exception);
	}

	public NewsDAOAddException(Exception exception) {
		super(exception);
	}
}
