<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		var="enterName" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.passRequirements"
		var="passReq" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterSurName"
		var="enterSurname" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterLog"
		var="enterLog" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterPass"
		var="enterPass" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterEmail"
		var="enterEmail" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.reg"
		var="regUser" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.nameErr"
		var="nameErr" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.surnameErr"
		var="surnameErr" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.loginErr"
		var="loginErr" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.passErr"
		var="passErr" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.emailErr"
		var="emailErr" />
<fmt:message bundle="${loc}" key="local.locbutton.name.emailUsed"
		var="emailUsed" />
		<fmt:message bundle="${loc}" key="local.locbutton.name.loginlUsed"
		var="loginUsed" />
		<fmt:message bundle="${loc}" key="local.locbutton.name.passReqInscription"
		var="passReqInscription" />		

	<div class="reg">
		<form action="controller" method="post">

			<input type="hidden" name="command" value="do_registration" /> <input
				type="hidden" name="local" value="${local}" /> 
				
			<label>	${enterName} <br /> <input type="text" name="name"
				value="${requestScope.name}" size=15 maxlength=30
				required="required" />
			</label>&nbsp		
															
			<c:if test="${requestScope.map.name eq 'not_correct'}">
				<font color="red"> <c:out value="${nameErr}" />	</font>				
			</c:if><br />

			<label>${enterSurname} <br /> <input type="text"
				name="surname" value="${requestScope.surname}" size=15 maxlength=30
				required="required" />
			</label>&nbsp

			<c:if test="${requestScope.map.surname eq 'not_correct'}">
				<font color="red"><c:out value="${surnameErr}" />  </font>				
			</c:if>	<br />
			
			<label> ${enterLog} <br /> <input type="text" name="login"
				value="${requestScope.login}" size=15 maxlength=30
				required="required" /></label> &nbsp

			<c:if test="${requestScope.map.login eq 'not_correct'}">
				<font color="red"> <c:out value="${loginErr}" />  </font>				
			</c:if>
			
			<c:if test="${requestScope.map.login eq 'used'}">
				<font color="red"> <c:out value="${loginUsed}" /> </font>				
			</c:if><br />

			<label> ${enterPass} <br /> <input type="password"
				name="password" value="" size=15 maxlength=30 required="required" /></label>
			&nbsp

			<c:if test="${requestScope.map.password eq 'not_correct'}">
				<font color="red"><c:out value="${passErr}" />  </font>			
			</c:if><br />

			
			<a href="controller?command=go_to_registration_page&local=${local}&viewPassReq=view">
				${passReq}</a> <br />
				
			 <label> ${enterEmail} <br /> <input type="email"
				name="email" value="${requestScope.email}" size=15 maxlength=30
				required="required" /></label> &nbsp

			<c:if test="${requestScope.map.email eq 'not_correct'}">
				<font color="red"> <c:out value="${emailErr}" />  </font>				
			</c:if>
			
			<c:if test="${requestScope.map.email eq 'used'}">
				<font color="red"> <c:out value='${emailUsed}'/>  </font>				
			</c:if>	<br />		
			
			<input type="submit" value="${regUser}" /><br />
			<br />
			<c:if test="${requestScope.viewPassReq eq 'view'}">
				<font color="red"> <c:out value='${passReqInscription}'/>  </font>				
			</c:if>	<br />
		</form>
	</div>
</body>
</html>
