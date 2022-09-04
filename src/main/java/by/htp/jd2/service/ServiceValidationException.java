package by.htp.jd2.service;

public class ServiceValidationException extends Exception {

	public ServiceValidationException() {
		super();
	}

	public ServiceValidationException(String message) {
		super(message);
	}

	public ServiceValidationException(Exception exception) {
		super(exception);
	}

	public ServiceValidationException(String message, Exception exception) {
		super(message, exception);
	}
}
