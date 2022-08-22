<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div>
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.news"
		var="news_show" />	
	<fmt:message bundle="${loc}" key="local.locbutton.name.latestNews"
		var="latest_news" />

	<div class="body-title">

		<a href="controller?command=go_to_news_list&local=${local}">${news_show} </a> ${latest_news}
	</div>

	<form action="command.do?method=delete" method="post">
		<c:if test="${not(requestScope.showNews eq 'notShow')}">
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
					</div>

				</div>

			</c:forEach>

			<div class="no-news">
				<c:if test="${requestScope.news eq null}">
               No news.
	       </c:if>
			</div>
		</c:if>

	</form>
</div>
