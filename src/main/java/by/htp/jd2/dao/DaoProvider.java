package by.htp.jd2.dao;

import by.htp.jd2.dao.impl.NewsDAOImpl;
import by.htp.jd2.dao.impl.UserDAOImpl;

public final class DaoProvider {
	private static final DaoProvider instance = new DaoProvider();

	private final UserDAO userDao = new UserDAOImpl();
	private final NewsDAO newsDAO = new NewsDAOImpl();

	private DaoProvider() {
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public NewsDAO getNewsDAO() {
		return newsDAO;
	}

	public static DaoProvider getInstance() {
		return instance;
	}
}
