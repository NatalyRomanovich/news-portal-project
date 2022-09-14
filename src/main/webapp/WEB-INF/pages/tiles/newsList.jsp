<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div>
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.news"
		var="newsShow" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.delete"
		var="delete" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.edit"
		var="editNews" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.view" var="view" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsList"
		var="newsList" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.regComplited "
		var="regComplited" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.dataSaved"
		var="dateSaved" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.dataDel"
		var="dateDel" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.dataChanged"
		var="dateChanged" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.noNews"
		var="noNews" />


	<div class="body-title">
		<a href="controller?command=go_to_news_list&local=${local}">${newsShow}
		</a> ${newsList}
	</div>
	<div>
		<c:if test="${(requestScope.reguser eq 'registered')}">
			<center>
				<font color="blue"> ${regComplited} </font>
			</center>
		</c:if>
		<br />

		<c:if test="${sessionScope.add eq 'done'}">
			<center>
				<font color="blue"> ${dateSaved} </font>
			</center>
		</c:if>
		<br />

		<c:if test="${sessionScope.delete eq 'done'}">
			<center>
				<font color="blue"> ${dateDel} </font>
			</center>
		</c:if>
		<br />

		<c:if test="${sessionScope.edit eq 'done'}">
			<center>
				<font color="blue"> ${dateChanged} </font>
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
						<a
							href="controller?command=go_to_view_news&idNews=${news.idNews}&local=${local}">...</a>
					</div>
					<div class="news-link-to-wrapper">
						<div class="link-position">
							<c:if test="${sessionScope.role eq 'admin'}">
								<a
									href="controller?command=go_to_do_action&commandsName=edit&idNews=${news.idNews}&local=${local}">${editNews}
								</a>
							</c:if>

							<a
								href="controller?command=go_to_view_news&idNews=${news.idNews}&local=${local}">${view}</a>

							<c:if test="${sessionScope.role eq 'admin'}">
								<input type="hidden" name="local" value="${local}" />
								<input type="checkbox" name="idNews" value="${news.idNews }" />
								<input type="hidden" name="command" value="delete_news" />
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>

		<div class="no-news">
			<c:if test="${requestScope.news eq null}">
              ${noNews}
	    </c:if>

		</div>
		<div class="second-button" align="right">

			<c:if test="${not(requestScope.news eq null)}">
				<c:if test="${sessionScope.role eq 'admin'}">
					<input type="submit" value="${delete}" />
					<br />
				</c:if>
			</c:if>

		</div>
	</form>
	<form>
		<div align="center" vertical-align="bottom">
			<c:if test="${not(requestScope.news eq null)}">
				<c:forEach items="${pages}" var="page">
					<a
						href="controller?command=go_to_news_list&pageNum=${page}&local=${local}">
						<c:out value="${page}" />
					</a>
				</c:forEach>
			</c:if>
		</div>
	</form>
</div>

