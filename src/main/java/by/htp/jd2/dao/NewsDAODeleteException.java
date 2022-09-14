package by.htp.jd2.dao;

public class NewsDAODeleteException extends Exception {

	private static final long serialVersionUID = 1L;

	public NewsDAODeleteException() {
		super();
	}

	public NewsDAODeleteException(String message) {
		super(message);
	}

	public NewsDAODeleteException(String message, Exception exception) {
		super(message, exception);
	}

	public NewsDAODeleteException(Exception exception) {
		super(exception);
	}
}
