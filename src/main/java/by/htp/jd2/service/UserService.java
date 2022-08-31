package by.htp.jd2.service;

import java.util.List;
import java.util.Map;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.controller.ComandForRegUser;

public interface UserService {

	String logIn(String login, String password) throws ServiceException;

	boolean registration(NewUserInfo user) throws ServiceException;

	Map<String, String> getValidatedParameters(NewUserInfo user) throws ServiceException;
}
