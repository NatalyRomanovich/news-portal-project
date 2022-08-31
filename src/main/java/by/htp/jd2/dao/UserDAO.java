package by.htp.jd2.dao;

import java.util.List;

import by.htp.jd2.bean.NewUserInfo;

public interface UserDAO {

	boolean logination(String login, String password) throws DaoException;

	boolean registration(NewUserInfo user) throws DaoException;

	String getRole(String login, String password) throws DaoException;

	List<String> getAlreadyUsedParameters(NewUserInfo user) throws DaoException;
}
