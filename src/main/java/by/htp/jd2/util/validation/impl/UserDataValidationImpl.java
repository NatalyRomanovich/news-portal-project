package by.htp.jd2.util.validation.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import by.htp.jd2.bean.ErrorsMessage;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.util.validation.UserDataValidation;

public class UserDataValidationImpl implements UserDataValidation{	
	
	private final String NAME_AND_SURNAME_CHECK = "[A-Z a-z]+";
	private final String LOGIN_AND_PASWORD_CHECK = "[A-Z a-z 0-9]+";
	private final String EMAIL_CHECK = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";	
	
	@Override
	public boolean checkAuthData(String login, String password){
		boolean result = false;
		
		if(password.isEmpty()||login.isEmpty()){
			result = false;
        }
        if (Pattern.matches(LOGIN_AND_PASWORD_CHECK, password) 
        		&& Pattern.matches(LOGIN_AND_PASWORD_CHECK, login)) {
        	result = true;
        }
       
		return result;
	}
	
	@Override
	public boolean checkRegData (NewUserInfo user) throws ServiceException {
		boolean result = false;
		   if (nameIsCorrect (user) && surNameIsCorrect(user) && loginIsCorrect(user) 
				   && passwordIsCorrect (user) && emailIsCorrect (user)) {
			   result =true;
		   }		   
		return result;
	}	
	
	public boolean nameIsNormal (NewUserInfo user) {
		boolean result = false;
		   if (Pattern.matches(NAME_AND_SURNAME_CHECK, user.getUsername())) {
			   result = true;	   
		   }		   
		return result;
	}
	
	public boolean nameIsCorrect (NewUserInfo user) {
		boolean result = false;
		   if (!user.getUsername().isEmpty() && nameIsNormal(user)) {
			   result = true;	   
		   }
		   else {			  
			   //throw new ServiceException("Name not entered"); 
		   }		   
		return result;
	}
	
	public boolean surNameIsNormal (NewUserInfo user) {
		boolean result = false;
		   if (Pattern.matches(NAME_AND_SURNAME_CHECK, user.getUserSurname())) {
			   result = true;	   
		   }		   
		return result;
	}
	
	public boolean surNameIsCorrect (NewUserInfo user) {
		boolean result = false;
		   if (!user.getUserSurname().isEmpty() && surNameIsNormal (user)) {
			   result = true;	   
		   }
		   else {			   
			  // throw new ServiceException("Surname not entered"); 
		   }		  		
		return result;
	}
	
	public boolean loginIsNormal (NewUserInfo user) {
		boolean result = false;
		   if (Pattern.matches(LOGIN_AND_PASWORD_CHECK, user.getLogin())) {
			   result = true;	   
		   }	
		return result;
	}
	
	public boolean loginIsCorrect (NewUserInfo user) {
		boolean result = false;
		   if (!user.getLogin().isEmpty() || loginIsCorrect (user)) {
			   result = true;	   
		   }
		   else {			   
			   //throw new ServiceException("Login not entered"); 
		   }		  		
		return result;
	}	
	
	public boolean passwordIsNormal (NewUserInfo user) {
		boolean result = false;
		if (Pattern.matches(LOGIN_AND_PASWORD_CHECK, user.getPassword())) {
			   result = true;	   
		   }				  		
		return result;
	}
	
	public boolean passwordIsCorrect (NewUserInfo user) {
		boolean result = false;
		   if (!user.getPassword().isEmpty() ||passwordIsNormal (user)) {
			   result = true;	   
		   }
		   else {			   
			   //throw new ServiceException("Password not entered"); 
		   }		  		
		return result;
	}
	
	public boolean emailIsNormal (NewUserInfo user) {
		boolean result = false;
		   if ((Pattern.matches(EMAIL_CHECK, user.getEmail()))) {
			   result = true;	   
		   }		   
		return result;
	}
	
	public boolean emailIsCorrect (NewUserInfo user) {
		boolean result = false;
		   if (!user.getEmail().isEmpty() || emailIsNormal ( user)) {
			   result = true;	   
		   }
		   else {
			   //throw new ServiceException("Email not entered"); 
		   }
		return result;
	}
}
