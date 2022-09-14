package by.htp.jd2.service;

public class ServiceValidationNewsException extends Exception {

	public ServiceValidationNewsException() {
		super();
	}

	public ServiceValidationNewsException(String message) {
		super(message);
	}

	public ServiceValidationNewsException(Exception exception) {
		super(exception);
	}

	public ServiceValidationNewsException(String message, Exception exception) {
		super(message, exception);
	}
}
