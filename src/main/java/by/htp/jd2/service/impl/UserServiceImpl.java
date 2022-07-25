package by.htp.jd2.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.dao.DaoException;
import by.htp.jd2.dao.DaoProvider;
import by.htp.jd2.dao.UserDAO;
import by.htp.jd2.dao.impl.UserDAOImpl;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.UserService;
import by.htp.jd2.util.validation.UserDataValidation;
import by.htp.jd2.util.validation.ValidationProvider;

public class UserServiceImpl implements UserService{

private final UserDAO userDAO = DaoProvider.getInstance().getUserDao();
private final UserDataValidation userDataValidation = ValidationProvider.getInstance().getUserDataValidation();

	@Override
	public String logIn(String login, String password) throws ServiceException {
		try {
	 		 			
		if(userDAO.logination(login, password)) {
			return userDAO.getRole(login, password);
				
			}else {
				return UsersRole.GUEST;
			}
			
		}catch(DaoException e) {
			throw new ServiceException(e);
		}
		
	}

	
	public boolean registration(NewUserInfo user) throws ServiceException  {
		  boolean result = false;
		  try {
			   if(userDataValidation.checkRegData (user)) {
			       result = userDAO.registration(user);				  
			       return result;
			   }
		   }catch(DaoException e) {
				throw new ServiceException(e);
			   
		   }		
		return result;			   
	   }	
}
