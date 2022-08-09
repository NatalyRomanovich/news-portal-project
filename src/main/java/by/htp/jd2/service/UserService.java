package by.htp.jd2.service;

import java.util.List;

import by.htp.jd2.bean.NewUserInfo;

public interface UserService {
	
	String logIn(String login, String password) throws ServiceException;
	boolean registration(NewUserInfo user) throws ServiceException;	
}
