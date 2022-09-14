package by.htp.jd2.dao;

public class NewsDAOUpdateException extends Exception {

	private static final long serialVersionUID = 1L;

	public NewsDAOUpdateException() {
		super();
	}

	public NewsDAOUpdateException(String message) {
		super(message);
	}

	public NewsDAOUpdateException(String message, Exception exception) {
		super(message, exception);
	}

	public NewsDAOUpdateException(Exception exception) {
		super(exception);
	}
}
