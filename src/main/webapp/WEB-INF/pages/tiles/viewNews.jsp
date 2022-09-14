<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div>
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsTitle"
		var="newsTitle" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsDate"
		var="newsDate" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsBrief"
		var="newsBrief" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsContent"
		var="newsContent" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.delete"
		var="delete" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.editNews"
		var="editNews" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.viewNews"
		var="viewInscription" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.news"
		var="newsShow" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.delCompletely"
		var="delCompl" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.cansel"
		var="cansel" />

	<div class="body-title">
		<a href="controller?command=go_to_news_list&local=${local}" />${newsShow}
		</a> ${viewInscription}
	</div>

	<div class="add-table-margin">
		<table class="news_text_format">
			<tr>
				<td class="space_around_title_text">${newsTitle}</td>

				<td class="space_around_view_text"><div class="word-breaker">
						<c:out value="${requestScope.news.title }" />
					</div></td>
			</tr>
			<tr>
				<td class="space_around_title_text">${newsDate}</td>

				<td class="space_around_view_text"><div class="word-breaker">
						<c:out value="${requestScope.news.newsDate }" />
					</div></td>
			</tr>
			<tr>
				<td class="space_around_title_text">${newsBrief}</td>
				<td class="space_around_view_text"><div class="word-breaker">
						<c:out value="${requestScope.news.briefNews }" />
					</div></td>
			</tr>
			<tr>
				<td class="space_around_title_text">${newsContent}</td>
				<td class="space_around_view_text"><div class="word-breaker">
						<c:out value="${requestScope.news.content }" />
					</div></td>
			</tr>
		</table>
	</div>


	<c:if test="${sessionScope.role eq 'admin'}">
		<div align="right">
			<form action="controller" method="post">
				<input type="hidden" name="command" value="go_to_do_action" /> <input
					type="hidden" name="local" value="${local}" /> <input
					type="hidden" name="idNews" value="${news.idNews}" /> <input
					type="hidden" name="pageNum" value="${pageNum}" /> <input
					type="hidden" name="commandsName" value="edit" /> <input
					type="submit" value="${editNews}" />
			</form><br />

			<form action="controller" method="post">
				<input type="hidden" name="command" value="delete_news" /> <input
					type="hidden" name="local" value="${local}" /> <input
					type="hidden" name="pageNum" value="${pageNum}" /> <input
					type="hidden" name="idNews" value="${news.idNews}" /> <input
					type="submit" value="${delete}" />
			</form><br />

			<form action="controller" method="post">
				<input type="hidden" name="command" value="go_to_news_list" /> <input
					type="hidden" name="local" value="${local}" /> <input
					type="hidden" name="pageNum" value="${pageNum}" /> <input
					type="submit" value="${cansel}" />

			</form>
		</div>

		<div align="left">

			<form action="controller" method="post">
				<input type="hidden" name="command" value="completely_delete_news" />
				<input type="hidden" name="local" value="${local}" /> <input
					type="hidden" name="pageNum" value="${pageNum}" /> <input
					type="hidden" name="idNews" value="${news.idNews}" /> <font
					color="red"> <input type="submit" value="${delCompl}" /></font>
			</form>
		</div>
	</c:if>
</div>
