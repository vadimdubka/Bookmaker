<%@ page contentType="text/html;charset=UTF-8" %>
<section class="section-user-menu col-s-5 col-3 col-float-right">
    <div class="section-header"><h2>Аккаунт пользователя</h2></div>
    <div class="section-content">
        <p>${sessionScope.user.role} ${sessionScope.user.email}</p>
        <c:choose>
            <c:when test="${sessionScope.user.role.role=='player'}">
                <div class="header-player-info">
                    <table>
                        <tr><td>Статус верификации:</td><td>${sessionScope.player.verification.verificationStatus.status}</td></tr>
                        <tr><td>Статус игрока:</td><td>${sessionScope.player.account.status.status.status}</td></tr>
                        <tr><td>Лимит по ставкам:</td><td>${sessionScope.player.account.status.betLimit}</td></tr>
                        <tr><td>Лимит на вывод в месяц:</td><td>${sessionScope.player.account.status.withdrawalLimit}</td></tr>
                        <tr><td>Снято в этом месяце:</td><td>${sessionScope.player.account.thisMonthWithdrawal}</td></tr>
                        <tr><td>Баланс:</td><td>${sessionScope.player.account.balance}</td></tr>
                    </table>
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