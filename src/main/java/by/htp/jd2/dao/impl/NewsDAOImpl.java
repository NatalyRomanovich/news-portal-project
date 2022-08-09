package by.htp.jd2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.jd2.bean.News;
import by.htp.jd2.dao.NewsDAO;
import by.htp.jd2.dao.NewsDAOException;

public class NewsDAOImpl implements NewsDAO {

	@Override
	public List<News> getLatestsList(int count) throws NewsDAOException {
		List<News> result = new ArrayList<News>();

		result.add(new News(1, "title1", "brief1brief1brief1brief1brief1brief1brief1", "contect1", "11/11/22"));
		result.add(new News(2, "title2", "brief2brief2brief2brief2brief2brief2brief2", "contect2", "11/11/22"));
		result.add(new News(3, "title3", "brief3brief3brief3brief3brief3brief3brief3", "contect3", "11/11/22"));
		result.add(new News(4, "title4", "brief4brief4brief4brief4brief4brief4brief4", "contect4", "11/11/22"));
		result.add(new News(5, "title5", "brief5brief5brief5brief5brief5brief5brief5", "contect5", "11/11/22"));

		return result;
	}

	@Override
	public List<News> getList() throws NewsDAOException {
		List<News> result = new ArrayList<News>();

		result.add(new News(1, "title1", "brief1brief1brief1brief1brief1brief1brief1", "contect1", "11/11/22"));
		result.add(new News(2, "title2", "brief2brief2brief2brief2brief2brief2brief2", "contect2", "11/11/22"));
		result.add(new News(3, "title3", "brief3brief3brief3brief3brief3brief3brief3", "contect3", "11/11/22"));
		result.add(new News(4, "title4", "brief4brief4brief4brief4brief4brief4brief4", "contect4", "11/11/22"));
		result.add(new News(5, "title5", "brief5brief5brief5brief5brief5brief5brief5", "contect5", "11/11/22"));
		result.add(new News(6, "title6", "brief5brief5brief5brief5brief5brief5brief6", "contect6", "11/11/22"));
		result.add(new News(7, "title7", "brief5brief5brief5brief5brief5brief5brief7", "contect7", "11/11/22"));
		return result;
	}

	@Override
	public News fetchById(int id) throws NewsDAOException {
		return new News(1, "title1", "brief1brief1brief1brief1brief1brief1brief1", "contect1", "11/11/22");
	}

	@Override
	public int addNews(News news) throws NewsDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateNews(News news) throws NewsDAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteNewses(String[] idNewses) throws NewsDAOException {
		// TODO Auto-generated method stub

	}

}
