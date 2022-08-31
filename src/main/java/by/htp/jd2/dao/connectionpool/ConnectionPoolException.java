package by.htp.jd2.dao.connectionpool;

public class ConnectionPoolException extends Exception {

	private static final long serialVersionID = 1L;

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Exception exception) {
		super(exception);
	}

	public ConnectionPoolException(String message, Exception exception) {
		super(message, exception);
	}
}
