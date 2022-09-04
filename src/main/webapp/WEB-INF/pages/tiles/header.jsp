<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="wrapper">
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
		var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en"
		var="en_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.logIn"
		var="log_in" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.logOut"
		var="log_out" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterLog"
		var="enter_log" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.enterPass"
		var="enter_pass" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.registration"
		var="registration" />
		<fmt:message bundle="${loc}" key="local.locbutton.name.errorLogination"
		var="logination_fail" />
		

	<div class="newstitle">News management</div>

	<div class="local-link">

		<div align="right">

			<a href="controller?command=change_language&local=en">
				${en_button} </a> &nbsp;&nbsp; <a
				href="controller?command=change_language&local=ru"> ${ru_button}

			</a> <br /> <br />

		</div>

		<c:if test="${not (sessionScope.user eq 'active')}">

			<div align="right">

				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_log_in" /> <input
						type="hidden" name="local" value="${local}" />${enter_log} <input
						type="text" name="login" value="" /><br /> ${enter_pass} <input
						type="password" name="password" value="" /><br />


					<c:if test="${not (requestScope.AuthenticationError eq null)}">
						<font color="red"> <c:out
								value="${logination_fail}" />
						</font>
					</c:if>

					<a href="controller?command=go_to_registration_page&local=${local}">${registration}
					</a> <input type="submit" value="${log_in}" /><br />
				</form>
			</div>

		</c:if>

		<c:if test="${sessionScope.user eq 'active'}">

			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_log_out" /> <input
						type="hidden" name="local" value="${local}" /> <input
						type="submit" value="${log_out}" /><br />
				</form>
			</div>

		</c:if>
	</div>

</div>

