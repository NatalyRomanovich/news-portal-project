package by.htp.jd2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import org.mindrot.jbcrypt.BCrypt;

import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.UsersParameter;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.dao.DaoException;
import by.htp.jd2.dao.UserDAO;
import by.htp.jd2.dao.UserRegistrationException;
import by.htp.jd2.dao.connectionpool.ConnectionPool;
import by.htp.jd2.dao.connectionpool.ConnectionPoolException;

public class UserDAOImpl implements UserDAO {

	private final static Logger LOG = LogManager.getLogger(UserDAOImpl.class);

	private static final Integer ID_UNKNOWN_ROLE = 3;
	private static final Integer FIRST_CHAR = 0;
	private static final Integer LAST_CHAR = 29;

	private static final String lOGIN_AND_PASSWORD_CHECK = "SELECT * FROM users WHERE login=?";

	@Override
	public boolean logination(String login, String password) throws DaoException {
		String hashPasswordInDB;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(lOGIN_AND_PASSWORD_CHECK)) {
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				LOG.log(Level.INFO, "Login not found");
				return false;
			}
			hashPasswordInDB = rs.getString(DatabaseTableColumn.TABLE_USERS_COLUMN_PASSWORD);
			if (!checkPassword(password, hashPasswordInDB)) {
				LOG.log(Level.INFO, "User not registered yet");
				return false;
			}
			return true;

		} catch (SQLException e) {
			throw new DaoException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new DaoException("Connection is not established", e);
		}

	}

	private boolean checkPassword(String password, String hashPasswordInDB) throws DaoException {
		String salt = hashPasswordInDB.substring(FIRST_CHAR, LAST_CHAR);
		String hashPasswordUser = BCrypt.hashpw(password, salt);
		return hashPasswordInDB.equals(hashPasswordUser);
	}

	private static final String ROLE_SEARCH = "SELECT * FROM users JOIN roles ON users.id_roles = roles.id_roles WHERE login=?";

	@Override
	public String getRole(String login, String password) throws DaoException {
		String role;

		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(ROLE_SEARCH)) {
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return UsersRole.GUEST.getTitle();
			}
			role = rs.getString(DatabaseTableColumn.TABLE_ROLES_COLUMN_TITLE);
			return role;
		} catch (SQLException e) {
			throw new DaoException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new DaoException("Connection is not established", e);
		}

	}

	private static final String ADDING_USER = "INSERT INTO users(login,password,name,surname,email,id_roles) values (?,?,?,?,?,?)";

	@Override
	public boolean registration(NewUserInfo user) throws DaoException {
		String password = String.valueOf(user.getPassword());
		int row;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(ADDING_USER)) {

			RegisrtationCheckInDB regisrtationCheckInDB = new RegisrtationCheckInDB(connection, user);

			if (regisrtationCheckInDB.isUsedLogin() || regisrtationCheckInDB.isUsedEmail()) {
				return false;
			}
			ps.setString(1, user.getLogin());
			ps.setString(2, hashPassword(password));
			ps.setString(3, user.getUsername());
			ps.setString(4, user.getUserSurname());
			ps.setString(5, user.getEmail());

			int idRole = getIdRoleByTitle(connection, UsersRole.USER.getTitle());
			ps.setInt(6, idRole);

			row = ps.executeUpdate();
			if (row == 0) {
				throw new UserRegistrationException("An error occurred while registering the user");
			}
			return true;

		} catch (SQLException e) {
			throw new DaoException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new DaoException("Connection is not established", e);

		} catch (UserRegistrationException e) {
			throw new DaoException(e);
		}
	}

	private String hashPassword(String password) throws DaoException {
		String salt = BCrypt.gensalt();
		String hashPassword = BCrypt.hashpw(password, salt);
		return hashPassword;
	}

	@Override
	public List<String> getUsersNotCorrectParameters(NewUserInfo user) throws DaoException {

		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(ADDING_USER)) {

			RegisrtationCheckInDB regisrtationCheckInDB = new RegisrtationCheckInDB(connection, user);
			List<String> notCorrectParameters = new ArrayList<String>();

			if (regisrtationCheckInDB.isUsedLogin()) {
				notCorrectParameters.add(UsersParameter.LOGIN_PARAM);
			}

			if (regisrtationCheckInDB.isUsedEmail()) {
				notCorrectParameters.add(UsersParameter.EMAIL_PARAM);

			}

			return notCorrectParameters;

		} catch (SQLException e) {
			throw new DaoException("Something is wrong with the database", e);

		} catch (ConnectionPoolException e) {
			throw new DaoException("Connection is not established", e);
		}
	}

	private static final String SEARCH_ROLE_ID = "SELECT * FROM roles WHERE title=?";

	private int getIdRoleByTitle(Connection connection, String role) throws SQLException {
		int idRole = ID_UNKNOWN_ROLE;

		PreparedStatement ps = connection.prepareStatement(SEARCH_ROLE_ID);
		ps.setString(1, role);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			idRole = rs.getInt(DatabaseTableColumn.TABLE_ROLES_COLUMN_ID_ROLES);
		}
		return idRole;
	}

	private class RegisrtationCheckInDB {

		private Connection connection;
		private NewUserInfo user;
		private boolean usedLogin;
		private boolean usedEmail;

		public RegisrtationCheckInDB() {
		}

		public RegisrtationCheckInDB(Connection connection, NewUserInfo user) throws SQLException {
			this.connection = connection;
			this.user = user;
			this.usedLogin = isLoginUsed(connection, user);
			this.usedEmail = isEmaillUsed(connection, user);
		}

		public boolean isUsedLogin() {
			return usedLogin;
		}

		public boolean isUsedEmail() {
			return usedEmail;
		}

		private static final String LOGIN_VERIFICATION = "SELECT * FROM users WHERE login=?";

		private boolean isLoginUsed(Connection connection, NewUserInfo user) throws SQLException {
			PreparedStatement ps = connection.prepareStatement(LOGIN_VERIFICATION);
			try {
				ps.setString(1, user.getLogin());
				ResultSet rs = ps.executeQuery();
				return rs.next();
			} finally {
				ps.close();
			}
		}

		private static final String EMAIL_VERIFICATION = "SELECT * FROM users WHERE email=?";

		private boolean isEmaillUsed(Connection connection, NewUserInfo user) throws SQLException {
			PreparedStatement ps = connection.prepareStatement(EMAIL_VERIFICATION);
			try {
				ps.setString(1, user.getEmail());
				ResultSet rs = ps.executeQuery();
				return rs.next();
			} finally {
				ps.close();
			}
		}
	}
}