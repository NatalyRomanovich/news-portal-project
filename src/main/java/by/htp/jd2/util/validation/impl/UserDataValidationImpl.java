package by.htp.jd2.util.validation.impl;


import java.util.ArrayList;
import java.util.List;

import by.htp.jd2.bean.ErrorsMessages;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.util.validation.UserDataValidation;

public class UserDataValidationImpl implements UserDataValidation{
	
	public List <String> errorsMessagesDataValidation = new ArrayList<>();
	private final String SYMBOL_COMMERCIAL_AT = "@";
	private final String SYMBOL_DOT = ".";
	
	@Override
	public boolean checkAuthData(String login, String password){
		boolean result = false;
		if (login != null & password != null) {
			result = true;
		} 		
		return result;
	}
	
	@Override
	public boolean checkRegData (NewUserInfo user) throws ServiceException {
		boolean result = false;
		if (nameExists (user) & surNameExists(user) & loginExists(user) & passwordExists (user) & emailIsCorrect (user)) {
			result =true;
		}
		  
		return result;
	}
	
	public List<String> getErrorsListMessage(String message){
		errorsMessagesDataValidation.add(message);
		System.out.println (errorsMessagesDataValidation);
		return errorsMessagesDataValidation;
	}
		
	public boolean nameExists (NewUserInfo user) {
		boolean result = false;
		if (user.getUsername() != null) {
			result = true;	   
		}
		else {
			getErrorsListMessage(ErrorsMessages.NAME_NOT_ENTERED);
			//throw new ServiceException("Name not entered"); 
		   }
		   
		return result;
	}
	
	public boolean surNameExists (NewUserInfo user) {
		boolean result = false;
		if (user.getUserSurname() != null) {
			result = true;	   
		}
		else {
			getErrorsListMessage(ErrorsMessages.SURNAME_NOT_ENTERED);
			// throw new ServiceException("Surname not entered"); 
		}
		  		
		return result;
	}
	
	public boolean loginExists (NewUserInfo user) {
		boolean result = false;
		if (user.getLogin() != null) {
			result = true;	   
		}
		else {
			getErrorsListMessage(ErrorsMessages.LOGIN_NOT_ENTERED);
			//throw new ServiceException("Login not entered"); 
		}
		  		
		return result;
	}
	
	public boolean passwordExists (NewUserInfo user) {
		boolean result = false;
		if (user.getPassword() != null) {
			result = true;	   
		}
		else {
			getErrorsListMessage(ErrorsMessages.PASSWORD_NOT_ENTERED);
			//throw new ServiceException("Password not entered"); 
		}		  		
		return result;
	}
	
	public boolean emailIsCorrect (NewUserInfo user) {
		boolean result = false;
		if (emailExists (user) & isEmailRight (user)) {
			result = true;	   
		}
		return result;
	}
	
	public boolean emailExists (NewUserInfo user) {
		boolean result = false;
		if (user.getEmail() != null) {
			result = true;	   
		}
		else {
			getErrorsListMessage(ErrorsMessages.EMAIL_NOT_ENTERED);
			//throw new ServiceException("Email not entered"); 
		   }		  		
		return result;
	}
	
	public boolean isEmailRight (NewUserInfo user) {
		boolean result = false;
		
                if (user.getEmail().contains(SYMBOL_COMMERCIAL_AT) &&user.getEmail().contains(SYMBOL_DOT)) {
            	      result = true;
                }
                else {
            	getErrorsListMessage(ErrorsMessages.EMAIL_ENTERED_INCORRECTLY);
 			//throw new ServiceException("Email entered incorrectly"); 
 		}            
		return result;	}
		
	
	
	/*public boolean checkOutLogin (String login, String password) {
		boolean result = false;
		if (login != null) {
			result = true;
		}
		return result;

	} 
	
	public boolean checkOutPassword (String login, String password) {
		boolean result = false;
		if (password != null) {
			result = true;
		}
		return result;

	} */	
}
