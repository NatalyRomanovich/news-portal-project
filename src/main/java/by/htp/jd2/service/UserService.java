package by.htp.jd2.service;

import java.util.Map;

import by.htp.jd2.bean.NewUserInfo;

public interface UserService {

	String logIn(String login, String password) throws ServiceException;

	Map<String, String> registrationResult(NewUserInfo user) throws ServiceException, ServiceRegistrationException, ServiceValidationException;
}
