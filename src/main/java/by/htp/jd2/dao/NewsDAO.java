package by.htp.jd2.dao;

import java.util.List;

import by.htp.jd2.bean.News;

public interface NewsDAO {
	List<News> getList(int pageNumber, int count) throws NewsDAOException;

	List<News> getLatestsList(int count) throws NewsDAOException;

	News fetchById(int id) throws NewsDAOException;

	boolean addNews(News news) throws NewsDAOException, NewsDAOAddException;

	boolean updateNews(News news) throws NewsDAOException, NewsDAOUpdateException;

	boolean deleteNews(String[] idNews) throws NewsDAOException, NewsDAODeleteException;

	boolean deleteNewsFromDB(String idNews) throws NewsDAOException;

	int getNewsListSize() throws NewsDAOException;
}
