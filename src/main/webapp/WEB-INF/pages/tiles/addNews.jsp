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
	<fmt:message bundle="${loc}" key="local.locbutton.name.addNews"
		var="add_news" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.news"
		var="news_show" />

	<div class="body-title">
		<a href="controller?command=go_to_news_list&local=${local}">${news_show}
		</a> ${add_news}<br /> <br />
	</div>
	<form action="controller" method="post">

		<input type="hidden" name="command" value="add_news" /> <label>
			${news_title}:<br /> <input type="text" name="title" value=""
			size=95 maxlength=150 required="required" /><br />
		</label> <label>${news_date}:<br /> <input type="date" name="date"
			value="" size=15 maxlength=150 required="required" /><br />
		</label> <label> ${news_brief}:<br /> <input type="text"
			name="briefNews" value="" size=95 maxlength=150 required="required" /><br />
		</label> <label> ${news_content}:<br /> <input type="text"
			name="content" value="" size=95 maxlength=10000 required="required" /><br />
		</label> <input type="submit" value="${save_news}" />
	</form>
	
	<div align="right">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="go_to_news_list" /> 
			<input type="hidden" name="local" value="${local}" />
			<input type="submit" value="${cansel}" /><br />
		</form>
	</div>
</body>
</html>
