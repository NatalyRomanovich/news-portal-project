<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
       <form action="controller" method="post">
		
		<input type="hidden" name="command" value="do_registration" />
		              						
		<label> Enter your name:<br />
		<input type="text" name="name" value="" size=15 maxlength=30 required="required"/><br /> </label>
        
                <label>Enter your surname:<br />
		<input type="text" name="surname" value="" size=15 maxlength=30 required="required"/><br /> </label>
						
		<label> Enter your login:<br />
		<input type="text" name="login" value="" size=15 maxlength=30 required="required"/><br /> </label>
		
		<label> Enter password:<br />
		
		<input type="password" name="password" value="" size=15 maxlength=30 required="required"/><br />  </label>
		
		<label> Enter email:<br />
		
		<input type="email" name="email" value="" size=15 maxlength=30 required="required"/><br />  </label>
				
		<input type="submit" value="Register" /><br /> 
        </form>
</body>
</html> 
