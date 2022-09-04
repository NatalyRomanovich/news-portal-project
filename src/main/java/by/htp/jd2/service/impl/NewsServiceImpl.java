package by.htp.jd2.service.impl;

import java.util.List;

import by.htp.jd2.bean.News;
import by.htp.jd2.dao.DaoException;
import by.htp.jd2.dao.DaoProvider;
import by.htp.jd2.dao.NewsDAO;
import by.htp.jd2.dao.NewsDAOException;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.util.UsersRole;
import by.htp.jd2.util.validation.impl.NewsValidation;

public class NewsServiceImpl implements NewsService {

	private final NewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();
	private NewsValidation.Validator validation = new NewsValidation.Validator();

	@Override
	public boolean save(News news) throws ServiceNewsException {
		try {
			NewsValidation newsValidation = validation.newsParameterIsCorrect(news).build();
			if (!newsValidation.newsParameterIsCorrect()) {
				return false;
			}
			return newsDAO.addNews(news);
		} catch (NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}

	@Override
	public void find() {
	}

	@Override
	public boolean update(News news) throws ServiceNewsException {
		try {
			return newsDAO.updateNews(news);
		} catch (NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}

	@Override
	public List<News> latestList(int count) throws ServiceNewsException {

		try {
			return newsDAO.getLatestsList(count);
		} catch (NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}

	@Override
	public List<News> list(int pageNumber, int count) throws ServiceNewsException {
		try {
			return newsDAO.getList(pageNumber, count);
		} catch (NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}
	
	@Override	
	public int getNewsListSize() throws ServiceNewsException {
		try {
			return newsDAO.getNewsListSize();
		} catch (NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}
	
	@Override
	public News findById(int id) throws ServiceNewsException {
		try {
			return newsDAO.fetchById(id);
		} catch (NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}

	@Override
	public boolean deleteNewsFromDB(String idNews) throws ServiceNewsException {
		try {
			return newsDAO.deleteNewsFromDB(idNews);

		} catch (NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}

	@Override
	public boolean deleteNews(String[] idNews) throws ServiceNewsException {
		try {
			return newsDAO.deleteNews(idNews);

		} catch (NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}
}