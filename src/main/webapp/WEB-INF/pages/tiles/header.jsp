<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="wrapper">
	<div class="newstitle">News management</div>


	<div class="local-link">

		<div align="right">

			<a href=""> en </a> &nbsp;&nbsp; 
			<a	href=""> ru </a> <br /> <br />
		</div>

		<c:if test="${not (sessionScope.user eq 'active')}">

			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_log_in" /> 
					Enter login: <input type="text" name="login" value="" /><br /> 
					Enter password: <input type="password" name="password" value="" /><br />

					<c:if test="${not (requestScope.AuthenticationError eq null)}">
						<font color="red"> 
						   <c:out value="${requestScope.AuthenticationError}" />
						</font> 
					</c:if>
					
					<a href="controller?command=go_to_registration_page">Registration</a> <input type="submit" value="Log In" /><br />
							
				</form>
			</div>

		</c:if>
		
		<c:if test="${sessionScope.user eq 'active'}">

			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_log_out" /> 
					<input type="submit" value="Log Out" /><br />
				</form>
			</div>

		</c:if>
	</div>

</div>
