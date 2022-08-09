package by.htp.jd2.service.impl;

import java.util.List;

import by.htp.jd2.bean.News;
import by.htp.jd2.dao.DaoProvider;
import by.htp.jd2.dao.NewsDAO;
import by.htp.jd2.dao.NewsDAOException;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;

public class NewsServiceImpl implements NewsService{

	private final NewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();
	private static final int lastNewsNumber = 5;
	
	@Override
	public void save() {
	}

	@Override
	public void find() {
	}

	@Override
	public void update() {
	}

	@Override
	public List<News> latestList(int count) throws ServiceException {
		
		try {
			return newsDAO.getLatestsList(lastNewsNumber);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> list() throws ServiceException {
		try {
			return newsDAO.getList();
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public News findById(int id) throws ServiceException {
		try {
			return newsDAO.fetchById(id);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}
}
