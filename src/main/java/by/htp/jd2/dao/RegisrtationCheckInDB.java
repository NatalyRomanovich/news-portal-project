package by.htp.jd2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.dao.connectionpool.ConnectionPool;
import by.htp.jd2.dao.connectionpool.ConnectionPoolException;

public class RegisrtationCheckInDB {
	
	Connection connection;
	NewUserInfo user;
	boolean correct = false;
	
	public RegisrtationCheckInDB() {
    }	
	
	public RegisrtationCheckInDB (Connection connection, NewUserInfo user) throws SQLException {
		this.connection = connection;
        this.user = user;
        
        if (isUserNotAlreadyRegistered (connection, user) && isLoginNotUsed (connection, user) && isEmailNotlUsed(connection, user)) {
        	this.correct = true;
        }
    }
	
	public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public NewUserInfo getUser() {
        return user;
    }

    public void setUser(NewUserInfo user) {
        this.user = user;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
	    
	private static final String USER_VERIFICATION = "SELECT * FROM users WHERE login=? AND password=? AND name=? AND surname=? AND email=?";
	private boolean isUserNotAlreadyRegistered (Connection connection, NewUserInfo user) throws SQLException {
				
		PreparedStatement ps = connection.prepareStatement(USER_VERIFICATION);
		ps.setString(1, user.getLogin());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getUsername());
		ps.setString(4, user.getUserSurname());
		ps.setString(5, user.getEmail());
			
		ResultSet rs = ps.executeQuery();
			
		if (rs.next()) {				
			return false;
		}
		return true;
	}
	
	private static final String LOGIN_VERIFICATION = "SELECT * FROM users WHERE login=?";
	public boolean isLoginNotUsed (Connection connection, NewUserInfo user) throws SQLException {
		
		PreparedStatement ps = connection.prepareStatement(LOGIN_VERIFICATION);
		ps.setString(1, user.getLogin());
		ResultSet rs = ps.executeQuery();
						
		if (rs.next()) {
			return false;
		}		
		return true;
	}
	
	private static final String EMAIL_VERIFICATION = "SELECT * FROM users WHERE email=?";
	public boolean isEmailNotlUsed (Connection connection, NewUserInfo user) throws SQLException {
		
		PreparedStatement ps = connection.prepareStatement(EMAIL_VERIFICATION);
		ps.setString(1, user.getEmail());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return false;
		}		
		return true;
	}
}	
