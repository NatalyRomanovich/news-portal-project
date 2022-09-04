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
	<fmt:message bundle="${loc}" key="local.locbutton.name.delCompletely"
		var="del_compl" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.regComplited "
		var="reg_complited" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.dataSaved"
		var="date_saved" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.dataDel"
		var="date_del" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.dataChanged"
		var="date_changed" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.noNews"
		var="no_news" />


	<div class="body-title">
		<a href="controller?command=go_to_news_list&local=${local}">${news_show}
		</a> ${news_list}
	</div>
	<div>
		<c:if test="${(requestScope.reguser eq 'registered')}">
			<center>
				<font color="blue"> ${reg_complited} </font>
			</center>
		</c:if>
		<br />

		<c:if test="${sessionScope.add eq 'done'}">
			<center>
				<font color="blue"> ${date_saved} </font>
			</center>
		</c:if>
		<br />

		<c:if test="${sessionScope.delete eq 'done'}">
			<center>
				<font color="blue"> ${date_del} </font>
			</center>
		</c:if>
		<br />

		<c:if test="${sessionScope.edit eq 'done'}">
			<center>
				<font color="blue"> ${date_changed} </font>
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
              ${no_news}
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
						href="controller?command=go_to_news_list&page_num=${page}&local=${local}">
						<c:out value="${page}" />
					</a>
				</c:forEach>
			</c:if>
		</div>
	</form>
</div>

