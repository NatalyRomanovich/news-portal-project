package by.htp.jd2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import by.htp.jd2.bean.ClientsRole;
import by.htp.jd2.bean.ErrorsMessage;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.dao.DaoException;
import by.htp.jd2.dao.UserDAO;
import by.htp.jd2.dao.connectionpool.ConnectionPool;
import by.htp.jd2.dao.connectionpool.ConnectionPoolException;

public class UserDAOImpl implements UserDAO{	
	
	public static List <String> errorsMessagesDAO = new ArrayList<>();
	private static final Integer ID_UNKNOWN_ROLE = 3;
	private int idRole;	
	private String role;
    		
	@Override
	public boolean logination(String login, String password) throws DaoException {
		boolean result = false;
		
		String sql = "SELECT * FROM users WHERE login=? AND password=?";
			
		try (Connection connection = ConnectionPool.getInstance().takeConnection();		
			PreparedStatement ps = connection.prepareStatement(sql)) {			
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				idRole = rs.getInt (DatabaseTableColumn.TABLE_USERS_COLUMN_ID_ROLES);
				roleSearch (connection,login, password);				
				result = true;					
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		
	    } catch (ConnectionPoolException e) {
		throw new DaoException(e);
	    }			  
		return result;		
	}
	
	public String getRole(String login, String password) throws DaoException {
				
		if (logination(login, password)) {			
			return role;
		}		
		return UsersRole.GUEST;
	}

	private void roleSearch (Connection connection, String login, String password) throws DaoException {
		
		String sql = "SELECT * FROM roles WHERE id_roles=" + idRole;
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				role = rs.getString(DatabaseTableColumn.TABLE_ROLES_COLUMN_TITLE);				
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}		
	}	
	
	@Override
	public boolean registration(NewUserInfo user) throws DaoException  {
		boolean result = false;
				
		String sql = "INSERT INTO users(login,password,name,surname,email,id_roles) values (?,?,?,?,?,?)";
						
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){					
			
			if (!isUserAlreadyRegistered(connection,user) && isLoginNotUsed (connection,user) && isEmailNotlUsed(connection,user)) {
			    ps.setString(1, user.getLogin());			    
			    ps.setString(2, user.getPassword());
			    ps.setString(3, user.getUsername());
			    ps.setString(4, user.getUserSurname());
			    ps.setString(5, user.getEmail());
			    getIdRoleByTitle (connection);
			    ps.setInt(6, idRole);
			    
			    ps.executeUpdate();
			    result = true;			    
			}		
			
		} catch (SQLException e) {
			return false;
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}				
		return result;     
	}
	
	private void getIdRoleByTitle (Connection connection) throws DaoException{
		
		String sql = "SELECT * FROM roles WHERE title=?";
				
		try {			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, UsersRole.USER);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				idRole = rs.getInt(DatabaseTableColumn.TABLE_ROLES_COLUMN_ID_ROLES);
				
			}
			else {
				
				idRole = ID_UNKNOWN_ROLE;
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}			
	}		
		
	public List <String> getErrorsListMessage (String message){
		errorsMessagesDAO.add(message);
		
		return errorsMessagesDAO;
	}	
	
	private boolean isUserAlreadyRegistered (Connection connection, NewUserInfo user) throws DaoException {
		boolean result = false;
		String sql = "SELECT * FROM users WHERE login=? AND password=? AND name=? AND surname=? AND email=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getUsername());
			ps.setString(4, user.getUserSurname());
			ps.setString(5, user.getEmail());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {				
				result = true;
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}		
		return result;				
	}
	
	public boolean isLoginNotUsed (Connection connection, NewUserInfo user) throws DaoException {
		boolean result = true;
		
		String sql = "SELECT * FROM users WHERE login=?";
		
		try {			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getLogin());
			ResultSet rs = ps.executeQuery();
						
			if (rs.next()) {
				result = false;
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}		
		return result;
	}
	
	public boolean isEmailNotlUsed (Connection connection,NewUserInfo user) throws DaoException {
		boolean result = true;
		String sql = "SELECT * FROM users WHERE email=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}		
		return result;
	}	
}	
