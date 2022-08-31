<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<body>
	<fmt:setLocale value="${sessionScope.local}" />
	<c:redirect url="controller?command=go_to_base_page&local=${local}" />

</body>
</html>
