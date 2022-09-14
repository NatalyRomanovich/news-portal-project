<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="wrapper">
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
		var="ruButton" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en"
		var="enButton" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.logIn"
		var="logIn" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.logOut"
		var="logOut" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterLog"
		var="enterLog" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterPass"
		var="enterPass" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.registration"
		var="registration" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.errorLogination"
		var="loginationFail" />


	<div class="newstitle">News management</div>

	<div class="local-link">

		<div align="right">

			<a href="controller?command=change_language&local=en">
				${enButton} </a> &nbsp;&nbsp; <a
				href="controller?command=change_language&local=ru"> ${ruButton}

			</a> <br /> <br />

		</div>

		<c:if test="${not (sessionScope.user eq 'active')}">

			<div align="right">

				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_log_in" /> <input
						type="hidden" name="local" value="${local}" />${enterLog} <input
						type="text" name="login" value="" /><br /> ${enterPass} <input
						type="password" name="password" value="" /><br />


					<c:if test="${not (requestScope.AuthenticationError eq null)}">
						<font color="red"> <c:out value="${loginationFail}" />
						</font>
					</c:if>

					<a href="controller?command=go_to_registration_page&local=${local}">${registration}
					</a> <input type="submit" value="${logIn}" /><br />
				</form>
			</div>

		</c:if>

		<c:if test="${sessionScope.user eq 'active'}">

			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_log_out" /> <input
						type="hidden" name="local" value="${local}" /> <input
						type="submit" value="${logOut}" /><br />
				</form>
			</div>

		</c:if>
	</div>

</div>

