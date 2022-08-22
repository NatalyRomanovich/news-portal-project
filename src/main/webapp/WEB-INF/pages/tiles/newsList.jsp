<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div>
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.news"
		var="news_show" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.delete"
		var="delete" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.edit"
		var="edit_news" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.view" var="view" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsList"
		var="news_list" />



	<div class="body-title">
		<a href="controller?command=go_to_news_list&local=${local}">${news_show} </a>
		${news_list}
	</div>
	<div>
		<c:if test="${(sessionScope.reguser eq 'registered')}">
			<center>
				<font color="blue"> Registration successfully completed! </font>
			</center>
		</c:if>
		<br />

		<c:if test="${sessionScope.add eq 'done'}">
			<center>
				<font color="blue"> Data saved successfully! </font>
			</center>
		</c:if>
		<br />

		<c:if test="${sessionScope.delete eq 'done'}">
			<center>
				<font color="blue"> Data deleted successfully! </font>
			</center>
		</c:if>
		<br />

		<c:if test="${sessionScope.edit eq 'done'}">
			<center>
				<font color="blue"> Data changed successfully! </font>
			</center>
		</c:if>
		<br />
	</div>
	<form action="controller" method="post">

		<c:forEach var="news" items="${requestScope.news}">
			<div class="single-news-wrapper">
				<div class="single-news-header-wrapper">
					<div class="news-title">
						<c:out value="${news.title}" />
					</div>
					<div class="news-date">
						<c:out value="${news.newsDate}" />
					</div>

					<div class="news-content">
						<c:out value="${news.briefNews}" />
					</div>
					<div class="news-link-to-wrapper">
						<div class="link-position">
							<c:if test="${sessionScope.role eq 'admin'}">
								<a
									href="controller?command=go_to_do_action&commandsName=edit&idNews=${news.idNews}&local=${local}">${edit_news}
								</a>
							</c:if>

							<a
								href="controller?command=go_to_view_news&idNews=${news.idNews}&local=${local}">${view}</a>

							<c:if test="${sessionScope.role eq 'admin'}">
								<input type="checkbox" name="idNews" value="${news.idNews }" />
								<input type="hidden" name="local" value="${local}" />
								<input type="hidden" name="command" value="delete_news" />
								
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>

		<!-- 	<logic:notEmpty name="newsForm" property="newsList">
		<div class="delete-button-position">
			<html:submit>
				<bean:message key="locale.newslink.deletebutton" />
			</html:submit>
		</div>
	</logic:notEmpty> -->

		<div class="no-news">
			<c:if test="${requestScope.news eq null}">
              No news.
	    </c:if>
		</div>
		<div class="second-button">

			<c:if test="${not(requestScope.news eq null)}">
				<c:if test="${sessionScope.role eq 'admin'}">
					<input type="submit" value="${delete}" />
					<br />
				</c:if>
			</c:if>

		</div>
	</form>
</div>

