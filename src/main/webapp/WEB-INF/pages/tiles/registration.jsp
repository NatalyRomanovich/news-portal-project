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
	<fmt:message bundle="${loc}" key="local.locbutton.name.passRequirements"
		var="pass_req" />
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

	<div class="reg">
		<form action="controller" method="post">

			<input type="hidden" name="command" value="do_registration" /> <input
				type="hidden" name="local" value="${local}" /> 
				
			<label>	${enter_name} <br /> <input type="text" name="name"
				value="${sessionScope.name}" size=15 maxlength=30
				required="required" />
			</label>&nbsp
							
			<c:if test="${requestScope.nameError eq 'not_valid'}">
				<font color="red"> <c:out value="${nameErr}" />	</font>
				<br />
			</c:if>

			<label>${enter_surname} <br /> <input type="text"
				name="surname" value="${sessionScope.surname}" size=15 maxlength=30
				required="required" />
			</label>&nbsp

			<c:if test="${not(requestScope.surnameError eq null)}">
				<font color="red"><c:out value="${surnameErr}" />  </font>
				<br />
			</c:if>

			<label> ${enter_log} <br /> <input type="text" name="login"
				value="${sessionScope.login}" size=15 maxlength=30
				required="required" /></label> &nbsp

			<c:if test="${requestScope.loginError eq 'not_valid'}">
				<font color="red"> <c:out value="${loginErr}" />  </font>
				<br />
			</c:if>
			
			<c:if test="${requestScope.loginError eq 'used'}">
				<font color="red"> <c:out value="${loginUsed}" /> </font>
				<br />
			</c:if>

			<label> ${enter_pass} <br /> <input type="password"
				name="password" value="" size=15 maxlength=30 required="required" /></label>
			&nbsp

			<c:if test="${requestScope.passwordError eq 'not_valid'}">
				<font color="red"> <c:out value="${passErr}" />  </font>
				<br />
			</c:if>

			<label> ${pass_req}<br />
			</label> <label> ${enter_email} <br /> <input type="email"
				name="email" value="${sessionScope.email}" size=15 maxlength=30
				required="required" /></label> &nbsp

			<c:if test="${requestScope.emailError eq 'not_valid'}">
				<font color="red"> <c:out value="${emailErr}" />  </font>
				<br />
			</c:if>
			
			<c:if test="${requestScope.emailError eq 'used'}">
				<font color="red"> <c:out value='${emailUsed}'/>  </font>
				<br />
			</c:if>			
			
			<input type="submit" value="${reg_user}" /><br />
		</form>
	</div>
</body>
</html>
