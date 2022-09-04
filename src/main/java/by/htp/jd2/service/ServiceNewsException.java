package by.htp.jd2.service;

public class ServiceNewsException extends Exception {

	public ServiceNewsException() {
		super();
	}

	public ServiceNewsException(String message) {
		super(message);
	}

	public ServiceNewsException(Exception exception) {
		super(exception);
	}

	public ServiceNewsException(String message, Exception exception) {
		super(message, exception);
	}
}
