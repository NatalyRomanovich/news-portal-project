<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="body-title">
	<a href="controller?command=go_to_add_news_page">News >> </a> News List
</div>

<form action="" method="post">
                     <c:if test="${(sessionScope.reguser eq 'unregistered')}">
				     <c:import url="/WEB-INF/pages/tiles/registration.jsp" />
				     </c:if>
				     
				     <c:if test="${(sessionScope.reguser eq 'registered')}">
				     <center> <font color="blue">  Registration successfully completed! </font> </center>
				     </c:if> <br />
				     
				     <c:if test="${(sessionScope.addNeews eq 'done')}">
				     <center> <font color="blue">  Data saved successfully! </font> </center>
				     </c:if> <br />
				     
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
						      <a href="">edit link </a> 
						</c:if>
						
						<a href="controller?command=go_to_view_news&id=${news.idNews}">view link </a> 
   					    
   					    <c:if test="${sessionScope.role eq 'admin'}">
   					         <input type="checkbox" name="idNews" value="${news.idNews }" />
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
</form>
