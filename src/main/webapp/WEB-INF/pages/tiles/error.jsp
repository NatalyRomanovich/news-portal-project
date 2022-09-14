<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.locbutton.name.noNews"
	var="error" />
<head>
<meta charset="UTF-8">
<title>Error page</title>
</head>
<body>
<p> ${error}</p>
</body>
