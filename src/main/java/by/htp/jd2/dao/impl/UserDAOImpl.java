package by.htp.jd2.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import by.htp.jd2.bean.ClientsRole;
import by.htp.jd2.bean.ErrorsMessages;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.dao.DaoException;
import by.htp.jd2.dao.UserDAO;

public class UserDAOImpl implements UserDAO{

private DataBaseAnalogue dataBase = DataBaseAnalogue.getInstance ();
//private DataBaseAnalogue dataBase = null;
public List <String> errorsMessagesDAO = new ArrayList<>();
		
	public boolean isDataBaseAvailable (String login, String password) throws DaoException {
		boolean result = false;
            try {
            	 if (dataBase == null) {            		 
            		 getErrorsListMessage(ErrorsMessages.DATABASE_NOT_AVAILABLE);
            		 
                throw new SQLException("Database not available");
            	 }
            	 else {
            		 result = true;
            	 }
			     return result;
	             }catch(SQLException e) {
	 				throw new DaoException(e);
	 			}
	}
	
	
	@Override
	public boolean logination(String login, String password) throws DaoException {
		
		boolean result = false;
		if (isDataBaseAvailable (login, password)) {
			for (NewUserInfo user : dataBase.getDataBase()) {
		
                              if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
            	                   result = true;	            	
                             }
			}
		}			  
		return result;		
	}

	
	public String getRole(String login, String password) throws DaoException {
		String role = UsersRole.GUEST;
		if (logination(login, password)) {
			for (NewUserInfo user : dataBase.getDataBase()) {
				if (user.getLogin().equals(login)) {
				role = user.getRole();
				}
			}			
		}
		return role;	}

	@Override
	public boolean registration(NewUserInfo user) throws DaoException  {
		boolean result = false;		
		if (!isUserAlreadyRegistered (user)) {
			if (isLoginNotUsed (user) & isEmailNotlUsed (user)) {
			    addDataBase (user);			      
			    result = true;
			}						
		}						
		return result;        
	}
	
	
	public List <String> getErrorsListMessage (String message){
		errorsMessagesDAO.add(message);
		System.out.println (errorsMessagesDAO);
		return errorsMessagesDAO;
	}
	
	public DataBaseAnalogue addDataBase (NewUserInfo user) {
		dataBase.getDataBase().add(user);
		return dataBase;
    }
	
	
	private boolean isUserAlreadyRegistered (NewUserInfo user) {
		boolean result = true;
		for (NewUserInfo registredUsers : dataBase.getDataBase()) {
		    if (!user.equals(registredUsers)) {
			   result = false;			
		    }
		    else {
			getErrorsListMessage(ErrorsMessages.USER_ALREADY_REGISTRED);	
			//throw new SQLException("User already registered");				
			}
		}
		return result;
	
				
	}
	
	public boolean isLoginNotUsed (NewUserInfo user) {
		boolean result = true;
		for (NewUserInfo userRegistred : dataBase.getDataBase()) {
                     if (user.getLogin().equals(userRegistred.getLogin()) ) {
            	          result = false;
            	          break;
            }
                     else {
            	          getErrorsListMessage(ErrorsMessages.LOGIN_ALREADY_IN_USE);
   			  // throw new ServiceException("Login already in use"); 
   		   }
        }
		
	      return result;
	}
	
	public boolean isEmailNotlUsed (NewUserInfo user) {
		boolean result = true;
		for (NewUserInfo userRegistred : dataBase.getDataBase()) {
                      if (user.getEmail().equals(userRegistred.getEmail()) ) {
            	           result = false;
            	           break;
                      }
                      else {
            	           getErrorsListMessage(ErrorsMessages.EMAIL_ALREADY_IN_USE);
  			   //throw new ServiceException("Email already in use"); 
  		     }
                }		
		return result;
	}
	
}	
