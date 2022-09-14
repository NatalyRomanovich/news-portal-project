package by.htp.jd2.service.impl;

import java.util.List;

import by.htp.jd2.bean.News;
import by.htp.jd2.dao.DaoProvider;
import by.htp.jd2.dao.NewsDAODeleteException;
import by.htp.jd2.dao.NewsDAOAddException;
import by.htp.jd2.dao.NewsDAO;
import by.htp.jd2.dao.NewsDAOException;
import by.htp.jd2.dao.NewsDAOUpdateException;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceValidationNewsException;
import by.htp.jd2.util.validation.impl.NewsValidation;

public class NewsServiceImpl implements NewsService {

	private final NewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();
	private NewsValidation.Validator validation = new NewsValidation.Validator();
	private static final int FIRST_SYMBOL = 0;
	private static final int BRIEF_LENGTH = 100;

	@Override
	public boolean save(News news) throws ServiceNewsException, ServiceValidationNewsException {
		String briefNews = null;
		try {
			NewsValidation newsValidation = validation.newsParameterIsCorrectForAddNews(news).build();
			if (!newsValidation.newsParameterIsCorrectForAddNews()) {
				throw new ServiceValidationNewsException("Incorrect data entered");
			}
			if (news.getContent().length() > BRIEF_LENGTH) {
				briefNews = news.getContent().substring(FIRST_SYMBOL, BRIEF_LENGTH);
			} else {
				briefNews = news.getContent();
			}
			news.setBriefNews(briefNews);
			return newsDAO.addNews(news);
		} catch (NewsDAOAddException | NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}

	@Override
	public void find() {
	}

	@Override
	public boolean update(News news) throws ServiceNewsException, ServiceValidationNewsException {
		String briefNews = null;

		try {
			NewsValidation newsValidation = validation.newsParameterIsCorrectForEditNews(news).build();
			if (!newsValidation.newsParameterIsCorrectForEditNews()) {
				throw new ServiceValidationNewsException("Incorrect data entered");
			}
			if (news.getContent().length() > BRIEF_LENGTH) {
				briefNews = news.getContent().substring(FIRST_SYMBOL, BRIEF_LENGTH);
			} else {
				briefNews = news.getContent();
			}
			news.setBriefNews(briefNews);
			return newsDAO.updateNews(news);
		} catch (NewsDAOException | NewsDAOUpdateException e) {
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

		} catch (NewsDAOException | NewsDAODeleteException e) {
			throw new ServiceNewsException(e);
		}
	}
}