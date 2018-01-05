<%@ page contentType="text/html;charset=UTF-8" %>
<div class="wrapper">
    <div class="section-header"><h2>Аккаунт пользователя</h2></div>
    <div class="section-content">
        <p>${sessionScope.user.role} ${sessionScope.user.email}</p>
        <c:choose>
            <c:when test="${sessionScope.user.role.role=='player'}">
                <div class="header-player-info">
                    <p>Стутус: неверифицированный</p>
                    <p>Баланс: 20 р</p>
                </div>
                <div>
                    <ul>
                        <li><a href="#">История аккаунта</a></li>
                        <li><a href="#">Настройки аккаунта</a></li>
                    </ul>
                </div>
            </c:when>
        </c:choose>
        <a href="${pageContext.request.contextPath}/controller?command_type=logout">
            <fmt:message key="user.logout"/></a>
    </div>
</div>