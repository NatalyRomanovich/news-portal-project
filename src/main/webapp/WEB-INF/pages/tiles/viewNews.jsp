<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div>
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsTitle"
		var="news_title" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsDate"
		var="news_date" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsBrief"
		var="news_brief" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsContent"
		var="news_content" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.delete"
		var="delete" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.editNews"
		var="edit_news" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.viewNews"
		var="view_inscription" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.news"
		var="news_show" />

	<div class="body-title">
		<a href="controller?command=go_to_news_list&local=${local}"/>${news_show} </a>
		${view_inscription}
	</div>

	<div class="add-table-margin">
		<table class="news_text_format">
			<tr>
				<td class="space_around_title_text">${news_title}</td>

				<td class="space_around_view_text"><div class="word-breaker">
						<c:out value="${requestScope.news.title }" />
					</div></td>
			</tr>
			<tr>
				<td class="space_around_title_text">${news_date}</td>

				<td class="space_around_view_text"><div class="word-breaker">
						<c:out value="${requestScope.news.newsDate }" />
					</div></td>
			</tr>
			<tr>
				<td class="space_around_title_text">${news_brief}</td>
				<td class="space_around_view_text"><div class="word-breaker">
						<c:out value="${requestScope.news.briefNews }" />
					</div></td>
			</tr>
			<tr>
				<td class="space_around_title_text">${news_content}</td>
				<td class="space_around_view_text"><div class="word-breaker">
						<c:out value="${requestScope.news.content }" />
					</div></td>
			</tr>
		</table>
	</div>


	<c:if test="${sessionScope.role eq 'admin'}">
		<div class="first-view-button">
			<form action="controller" method="post">
				<input type="hidden" name="command" value="go_to_do_action" /> 
				<input type="hidden" name="local" value="${local}" />
				<input type="hidden" name="idNews" value="${news.idNews}" /> <input
					type="hidden" name="commandsName" value="edit" /> <input
					type="submit" value="${edit_news}" />
			</form>
		</div>

		<div class="second-view-button">
			<form action="controller" method="post">
				<input type="hidden" name="command" value="delete_news" /> 
				<input type="hidden" name="local" value="${local}" />
				<input type="hidden" name="idNews" value="${news.idNews}" /> 
				<input type="submit" value="${delete}" />
			</form>
		</div>
	</c:if>
</div>