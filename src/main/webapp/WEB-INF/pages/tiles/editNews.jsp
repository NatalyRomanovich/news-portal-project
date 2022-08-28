<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.editTitle"
		var="edit_inscription" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsTitle"
		var="news_title" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsDate"
		var="news_date" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsBrief"
		var="news_brief" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsContent"
		var="news_content" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.save"
		var="save_news" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.cansel"
		var="cansel" />
		<fmt:message bundle="${loc}" key="local.locbutton.name.news"
		var="news_show" />

<div class="body-title">
		<a href="controller?command=go_to_news_list&local=${local}">${news_show} </a> ${edit_inscription}<br />
		<br />
	</div>	

	<div align="left">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="edit_news" /> 
			<input type="hidden" name="local" value="${local}" />
			<input
				type="hidden" value="${news.idNews }" name="idNews" /> <br> <label>${news_title}</label><br>
			<input type="text" name="title" value="${news.title }" size=95
				maxlength=150 /><br /> <br> <label>${news_date}</label><br>
			<input type="text" name="date" value="${news.newsDate}" size=15
				maxlength=150 /><br /> <br> <label>${news_brief}</label><br>
			<input type="text" name="briefNews" value="${news.briefNews}" size=95
				maxlength=150 /><br /> <br> <label>${news_content}</label><br>
			<input type="text" name="content" value="${news.content}" size=95
				maxlength=150 /><br /> <br> <input type="submit"
				value="${save_news}" />
		</form>
	</div>
	<div align="right">
		<form action="controller" method="post">
			<input type="hidden" name="idNews" value="${news.idNews}" />
			<input type="hidden" name="command" value="go_to_view_news" /> 
			<input type="hidden" name="local" value="${local}" />
			<input type="submit" value="${cansel}" />
		</form>
	</div>

</body>
</html>