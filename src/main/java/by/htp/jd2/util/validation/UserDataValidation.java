package by.htp.jd2.util.validation;

import java.util.List;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.service.ServiceException;

public interface UserDataValidation {
	boolean checkAuthData(String login, String password) throws ServiceException;

	boolean checkRegData(NewUserInfo user) throws ServiceException;
}
