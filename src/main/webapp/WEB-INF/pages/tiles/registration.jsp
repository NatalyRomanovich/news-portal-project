<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<fmt:setLocale value="${pageContext.response.locale}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterName"
		var="enter_name" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterSurName"
		var="enter_surname" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterLog"
		var="enter_log" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterPass"
		var="enter_pass" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterEmail"
		var="enter_email" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.reg"
		var="reg_user" />
	<div class="reg">
		<form action="controller" method="post">

			<input type="hidden" name="command" value="do_registration" /> 
			<input type="hidden" name="local" value="${local}" />
			
			<label>	${enter_name}  	<br />
			<input type="text" name="name" value="${requestScope.name}" size=15 maxlength=30 required="required" /></label>&nbsp  
			<font color="red"> 
			${sessionScope.nameError} </font> <br /> 	 		
			
			<label>${enter_surname}  <br />
			<input type="text" name="surname" value="${requestScope.surname}" size=15 maxlength=30 required="required" /> </label>&nbsp 
			<font color="red"> 
			${sessionScope.surnameError} </font> <br /> 
			
			<label> ${enter_log} <br />
			<input type="text" name="login" value="${requestScope.login}" size=15 maxlength=30 required="required" /></label> &nbsp 
			<font color="red"> 
			${sessionScope.loginError} </font> <br /> 
			
			<label> ${enter_pass} <br />
			<input type="password" name="password" value="" size=15 maxlength=30 required="required" /></label> &nbsp
			<font color="red"> 
			${sessionScope.passwordError}</font> <br /> 
			
			<label> ${pass_req}<br /> </label> 
			
			<label> ${enter_email} <br />
			<input type="email"name="email" value="${requestScope.email}" size=15 maxlength=30 required="required" /></label> &nbsp
			<font color="red"> 
			${sessionScope.emailError}  </font> <br /> 
			
			<input type="submit" value="${reg_user}" /><br />
		</form>
	</div>
</body>
</html>
