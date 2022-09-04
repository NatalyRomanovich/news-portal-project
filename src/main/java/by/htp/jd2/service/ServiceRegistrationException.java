package by.htp.jd2.service;

public class ServiceRegistrationException extends Exception {

	public ServiceRegistrationException() {
		super();
	}

	public ServiceRegistrationException(String message) {
		super(message);
	}

	public ServiceRegistrationException(Exception exception) {
		super(exception);
	}

	public ServiceRegistrationException(String message, Exception exception) {
		super(message, exception);
	}
}
