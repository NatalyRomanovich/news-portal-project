package by.htp.jd2.service.impl;

import java.util.List;

import by.htp.jd2.bean.News;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.dao.DaoException;
import by.htp.jd2.dao.DaoProvider;
import by.htp.jd2.dao.NewsDAO;
import by.htp.jd2.dao.NewsDAOException;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceNewsException;

public class NewsServiceImpl implements NewsService {

	private final NewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();	

	@Override
	public boolean save (News news) throws ServiceNewsException {
		try {
			if (newsDAO.addNews(news) == 0) {
				return false;
			}
			return true;
		} catch (NewsDAOException e) {
			throw new ServiceNewsException(e);
		}
	}

	@Override
	public void find() {
	}

	@Override
	public void update() {
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
	public List<News> list() throws ServiceNewsException {
		try {
			return newsDAO.getList();
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
}
