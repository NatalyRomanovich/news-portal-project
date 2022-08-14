package by.htp.jd2.dao;

import java.util.List;

import by.htp.jd2.bean.News;

public interface NewsDAO {
	List<News> getList() throws NewsDAOException;

	List<News> getLatestsList(int count) throws NewsDAOException;

	News fetchById(int id) throws NewsDAOException;

	int addNews(News news) throws NewsDAOException;

	boolean updateNews(News news) throws NewsDAOException;

	boolean deleteNewses(String[] idNewses) throws NewsDAOException;
}
