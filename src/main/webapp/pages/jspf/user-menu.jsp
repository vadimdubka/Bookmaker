<%@ page contentType="text/html;charset=UTF-8" %>
<section class="section-user-menu col-s-5 col-3 col-float-right">
    <div class="section-header"><h2>Аккаунт пользователя</h2></div>
    <div class="section-content">
        <p><b>${sessionScope.user.role} ${sessionScope.user.email}</b></p>
        <c:choose>
            <c:when test="${sessionScope.user.role.role=='player'}">
                <div class="header-player-info">
                    <table class="user-menu">
                        <tr>
                            <td class="name">Статус верификации:</td>
                            <td class="info">${sessionScope.player.verification.verificationStatus.status}</td>
                        </tr>
                        <tr>
                            <td class="name">Статус игрока:</td>
                            <td class="info">${sessionScope.player.account.status.status.status}</td>
                        </tr>
                        <tr>
                            <td class="name">Лимит по ставкам:</td>
                            <td class="info">${sessionScope.player.account.status.betLimit}</td>
                        </tr>
                        <tr>
                            <td class="name">Лимит на вывод в месяц:</td>
                            <td class="info">${sessionScope.player.account.status.withdrawalLimit}</td>
                        </tr>
                        <tr>
                            <td class="name">Снято в этом месяце:</td>
                            <td class="info">${sessionScope.player.account.thisMonthWithdrawal}</td>
                        </tr>
                        <tr>
                            <td class="name">Баланс:</td>
                            <td class="info">${sessionScope.player.account.balance}</td>
                        </tr>
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