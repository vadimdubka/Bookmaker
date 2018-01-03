<%@ page contentType="text/html;charset=UTF-8" %>

<section class="block-float border-test player">
    <div class="section-header">Аккаунт пользователя</div>
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
</section>