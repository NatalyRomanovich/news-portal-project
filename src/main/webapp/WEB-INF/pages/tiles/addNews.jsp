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
	<fmt:message bundle="${loc}" key="local.locbutton.name.save"
		var="saveNews" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.cansel"
		var="cansel" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.addNews"
		var="addNews" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.news"
		var="newsShow" />

	<div class="body-title">
		<a
			href="controller?command=go_to_news_list&local=${local}">${newsShow}
		</a> ${addNews}<br /> <br />
	</div>
	<form action="controller" method="post">

		<input type="hidden" name="command" value="add_news" /> <label>
			${newsTitle}:<br /> <input type="text" name="title" value="" size=95
			maxlength=150 required="required" /><br />
		</label> <label>${newsDate}:<br /> <input type="date" name="date"
			value="" size=15 maxlength=150 required="required" /><br />
		</label>

		<p>
			<c:out value="${newsContent}" />
		</p>
		<textarea name="content" cols="95" rows="10" maxlength="10000"
			required="required"></textarea>
		<br /> <input type="submit" value="${saveNews}" />

	</form>

	<div align="right">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="go_to_news_list" /> <input
				type="hidden" name="local" value="${local}" /> <input type="hidden"
				name="pageNum" value="${pageNum}" /> <input type="submit"
				value="${cansel}" /><br />
		</form>
	</div>
</body>
</html>
