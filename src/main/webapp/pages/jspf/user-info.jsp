<%@ page contentType="text/html;charset=UTF-8" %>
<div class="player-block container">
    <h4><fmt:message key="user.welcome"/>!</h4>
    <p>${user.role} ${user.email}, <fmt:message key="user.hello"/>!</p>
    <div class="custom-button">
        <a href="${pageContext.request.contextPath}/controller?command_type=logout"><fmt:message key="user.logout"/></a>
    </div>
</div>