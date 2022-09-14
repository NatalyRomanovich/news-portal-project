package by.htp.jd2.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import by.htp.jd2.bean.NewUserInfo;

public interface UserDAO {

	boolean logination(String login, String password) throws DaoException;

	boolean registration(NewUserInfo user) throws DaoException, UserDAORegistrationException;

	String getRole(String login, String password) throws DaoException;
	
	boolean isEmaillUsed(String email) throws DaoException;

	boolean isLoginUsed(String login) throws DaoException;
}
