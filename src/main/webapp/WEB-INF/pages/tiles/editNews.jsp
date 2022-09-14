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
		var="editInscription" />
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
	<fmt:message bundle="${loc}" key="local.locbutton.name.news"
		var="newsShow" />

	<div class="body-title">
		<a
			href="controller?command=go_to_news_list&local=${local}">${newsShow}
		</a> ${editInscription}<br /> <br />
	</div>

	<div align="left">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="edit_news" />
			
			<input	type="hidden" name="local" value="${local}" /> 
				
			<input type="hidden" name="pageNum" value="${pageNum}" />
			
			<input type="hidden" value="${news.idNews }" name="idNews" /> <br> 
				
			<label>${newsTitle}</label><br>
			
			<input type="text" name="title" value="${news.title}" size=95
				maxlength=150 /><br /> <br> 
				
			<label>${newsDate}</label><br>
			
			<input type="text" name="date" value="${news.newsDate}" size=15
				maxlength=150 /><br /> <br> 
				
			<label>${newsContent}</label><br>
			
			<p>
				<c:out value="${newsContent}" />
			</p>
			<textarea name="content" cols="95" rows="10" maxlength="10000">${news.content}</textarea>
			<br /> <br> 
			
			<input type="submit" value="${saveNews}" />

		</form>
	</div>
	<div align="right">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="go_to_news_list" /> <input
				type="hidden" name="local" value="${local}" /> <input type="hidden"
				name="pageNum" value="${pageNum}" /> <input type="submit"
				value="${cansel}" />
		</form>
	</div>

</body>
</html>