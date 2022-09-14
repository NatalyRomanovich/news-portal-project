package by.htp.jd2.service;

import java.util.List;

import by.htp.jd2.bean.News;
import by.htp.jd2.dao.NewsDAOException;

public interface NewsService {

	void find() throws ServiceNewsException;

	boolean update(News news) throws ServiceNewsException, ServiceValidationNewsException;

	List<News> latestList(int count) throws ServiceNewsException;

	List<News> list(int pageNumber, int count) throws ServiceNewsException;

	News findById(int id) throws ServiceNewsException;

	boolean save(News news) throws ServiceNewsException, ServiceValidationNewsException;

	boolean deleteNews(String[] idNewses) throws ServiceNewsException;

	boolean deleteNewsFromDB(String idNews) throws ServiceNewsException;

	int getNewsListSize() throws ServiceNewsException;

}
