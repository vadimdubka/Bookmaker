<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.admin!=null || sessionScope.analyst!=null}">
        <jsp:forward page="${pageContext.request.contextPath}/employee/main.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="${pageContext.request.contextPath}/controller?command_type=goto_main"/>
    </c:otherwise>
</c:choose>
</body>
</html>