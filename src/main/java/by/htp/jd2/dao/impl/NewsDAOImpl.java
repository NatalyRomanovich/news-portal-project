package by.htp.jd2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import by.htp.jd2.bean.News;
import by.htp.jd2.dao.NewsDAOAddException;
import by.htp.jd2.dao.NewsDAO;
import by.htp.jd2.dao.NewsDAOException;
import by.htp.jd2.dao.NewsDAODeleteException;
import by.htp.jd2.dao.NewsDAOUpdateException;
import by.htp.jd2.dao.connectionpool.ConnectionPool;
import by.htp.jd2.dao.connectionpool.ConnectionPoolException;

public class NewsDAOImpl implements NewsDAO {

	private static final int ADMIN_ROLE_ID = 1;
	private static final String DATE_FORMAT_FOR_VIEW = "dd/MM/yyyy";
	private static final String DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd";
	private static final String NEWS_DELETED = "yes";
	private static final String IS_NOT_DELETED_NEWS = "no";
	private static final String COUNT = "COUNT(*)";

	private static final String SHOW_LATEST_NEWS_FOR_GUEST = "SELECT * FROM news WHERE deleted=? ORDER BY date DESC LIMIT ?";

	@Override
	public List<News> getLatestsList(int count) throws NewsDAOException {
		List<News> result = new ArrayList<News>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(SHOW_LATEST_NEWS_FOR_GUEST)) {

			ps.setString(1, IS_NOT_DELETED_NEWS);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String date = convertDateToString(rs);
				News newsInLatestsList = new News(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						date);
				result.add(newsInLatestsList);
			}

			return result;

		} catch (SQLException e) {
			throw new NewsDAOException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Connection is not established", e);
		}
	}

	private static final String SHOW_NEWS_ON_ONE_PAGE = "SELECT * FROM news WHERE deleted=? ORDER BY date DESC, id_news DESC LIMIT ? OFFSET ?";

	@Override
	public List<News> getList(int pageNumber, int count) throws NewsDAOException {
		List<News> result = new ArrayList<News>();

		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(SHOW_NEWS_ON_ONE_PAGE)) {
			ps.setString(1, IS_NOT_DELETED_NEWS);
			ps.setInt(2, count);
			int offset = count * (pageNumber - 1);
			ps.setInt(3, offset);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String date = convertDateToString(rs);
				News news = new News(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), date);
				result.add(news);
			}
			return result;

		} catch (SQLException e) {
			throw new NewsDAOException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Connection is not established", e);
		}
	}

	private static final String NUMBER_OF_NEWS_SELECT = "SELECT count(*) FROM news WHERE deleted=?";

	@Override
	public int getNewsListSize() throws NewsDAOException {

		int count = 0;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(NUMBER_OF_NEWS_SELECT)) {
			ps.setString(1, IS_NOT_DELETED_NEWS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(COUNT);
			}
			return count;

		} catch (SQLException e) {
			throw new NewsDAOException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Connection is not established", e);
		}
	}

	private static final String VIEW_NEWS_BY_ID = "SELECT * FROM news WHERE id_news=?";

	@Override
	public News fetchById(int id) throws NewsDAOException {

		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(VIEW_NEWS_BY_ID)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {

				return null;
			}
			String date = convertDateToString(rs);

			return new News(id, rs.getString(2), rs.getString(3), rs.getString(4), date);

		} catch (SQLException e) {
			throw new NewsDAOException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Connection is not established", e);
		}
	}

	private static final String ADD_NEWS = "INSERT INTO news (title,brief,content,date,users_id) VALUE(?,?,?,?,?)";

	@Override
	public boolean addNews(News news) throws NewsDAOException, NewsDAOAddException {
		int row = 0;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(ADD_NEWS)) {

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBriefNews());
			ps.setString(3, news.getContent());

			Timestamp ts = convertDateToDBFormat(news.getNewsDate(), DATE_FORMAT_FROM_SERVER);
			ps.setTimestamp(4, ts);
			ps.setInt(5, ADMIN_ROLE_ID);
			row = ps.executeUpdate();
			if (row == 0) {
				throw new NewsDAOAddException("An error occurred while adding news to the database");
			}
			return true;
		} catch (SQLException e) {
			throw new NewsDAOException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Connection is not established", e);
		}
	}

	private static final String UPDATE_NEWS = "UPDATE news SET title = ? , brief = ?, content = ? , date = ? WHERE id_news = ?";

	@Override
	public boolean updateNews(News news) throws NewsDAOException, NewsDAOUpdateException {
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_NEWS)) {

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBriefNews());
			ps.setString(3, news.getContent());
			Timestamp ts = convertDateToDBFormat(news.getNewsDate(), DATE_FORMAT_FOR_VIEW);
			ps.setTimestamp(4, ts);
			ps.setInt(5, news.getIdNews());
			int rs = ps.executeUpdate();

			if (rs == 0) {
				throw new NewsDAOUpdateException("An error occurred while adding news to the database");
			}
			return true;

		} catch (SQLException e) {
			throw new NewsDAOException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Connection is not established", e);
		}
	}

	private static final String DELETE_NEWS = "UPDATE news SET deleted=? WHERE id_news = ?";

	private static final String DELETE_NEWS_COMPLETELY_FROM_DB = "DELETE FROM news WHERE id_news = ?";

	@Override
	public boolean deleteNewsFromDB(String idNews) throws NewsDAOException {
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_NEWS_COMPLETELY_FROM_DB)) {
			ps.setInt(1, Integer.parseInt(idNews));
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new NewsDAOException("Something is wrong with the database", e);
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Connection is not established", e);
		}
		return true;
	}

	@Override
	public boolean deleteNews(String[] idNews) throws NewsDAOException, NewsDAODeleteException {
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_NEWS)) {

			if (!deleteAllNews(connection, idNews)) {
				return false;
			}
			return true;

		} catch (SQLException e) {
			throw new NewsDAOException("Something is wrong with the database", e);
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Connection is not established", e);
		} 
	}

	public boolean deleteAllNews(Connection connection, String[] idNews) throws SQLException, NewsDAODeleteException {
		PreparedStatement ps = connection.prepareStatement(DELETE_NEWS);
		try {
			connection.setAutoCommit(false);
			for (String id : idNews) {
				ps.setString(1, NEWS_DELETED);
				ps.setInt(2, Integer.parseInt(id));
				ps.executeUpdate();
			}
			connection.commit();
			connection.setAutoCommit(true);

		} catch (SQLException e) {
			connection.rollback();
			throw new NewsDAODeleteException(e);
		} finally {
			ps.close();
		}
		return true;
	}

	private Timestamp convertDateToDBFormat(String dateStr, String format) throws NewsDAOException {
		try {
			DateFormat formatter = new SimpleDateFormat(format);
			Date date = formatter.parse(dateStr);
			Timestamp ts = new Timestamp(date.getTime());
			return ts;
		} catch (ParseException e) {
			throw new NewsDAOException(e);
		}
	}

	private String convertDateToString(ResultSet rs) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FOR_VIEW);
		Timestamp timestamp = rs.getTimestamp(5);
		String date = sdf.format(timestamp);
		return date;
	}
}
