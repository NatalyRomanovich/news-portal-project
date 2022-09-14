<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div>
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsTitle"
		var="newsShow" />	
	<fmt:message bundle="${loc}" key="local.locbutton.name.addNews"
		var="addNews" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.newsList"
		var="newsList" />
	<div class="menu-wrapper">
		<div class="menu-title-wrapper">
			<div class="menu-title">${newsShow}</div>
		</div>

		<div class="list-menu-invisible-wrapper">
			<div class="list-menu-wrapper" style="float: right;">
				<ul style="list-style-image: url(images/img.jpg); text-align: left;">
					<li style="padding-left: 15px;"><a
						href="controller?command=go_to_news_list&local=${local}&pageNum=${pageNum}">${newsList}</a><br /></li>

					<c:if test="${sessionScope.role eq 'admin'}">
						<li style="padding-left: 15px;"><a
							href="controller?command=go_to_do_action&commandsName=add&local=${local}">${addNews} </a> <br /></li>
					</c:if>
				</ul>
			</div>
			<div class="clear"></div>
		</div>
		<!--  grey free space at the bottom of menu -->
		<div style="height: 25px;"></div>
	</div>
</div>
