<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main class="row container">
    <section class="section-center col-s-7">
        <div class="section-header"><h2>Состояние и история аккаунта</h2></div>
        <div class="section-content">
            <p><b>${sessionScope.user.role} ${sessionScope.user.email}</b></p>
            <div class="col-s-8 col-4">
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

            <table class="bets user-state">
                <tr>
                    <th colspan="5">Cтавка</th>
                    <th colspan="3">Событие</th>
                </tr>
                <tr>
                    <th>Статус</th>
                    <th>Дата</th>
                    <th>Сумма</th>
                    <th>Коэфф.</th>
                    <th>Тип</th>
                    <th>Результ</th>
                    <th>Дата</th>
                    <th>№</th>
                </tr>
                <c:forEach var="bet" items="${requestScope.bet_list}">
                    <c:set var="status" value="${bet.status.status}"/>
                    <tr>
                        <c:choose>
                            <c:when test="${status == 'win'}">
                                <td class="bet-status bet-status-win" rowspan="3">${bet.status.status}</td>
                            </c:when>
                            <c:when test="${status == 'losing'}">
                                <td class="bet-status bet-status-losing" rowspan="3">${bet.status.status}</td>
                            </c:when>
                            <c:when test="${status == 'paid'}">
                                <td class="bet-status bet-status-paid" rowspan="3">${bet.status.status}</td>
                            </c:when>
                            <c:otherwise>
                                <td class="bet-status" rowspan="3">${bet.status.status}</td>
                            </c:otherwise>
                        </c:choose>
                        <td rowspan="3">${j:formatDateTime(bet.date, "yyyy.MM.dd HH:mm")}</td>
                        <td rowspan="3">${bet.amount}</td>
                        <td rowspan="3">${bet.coefficient}</td>
                        <td rowspan="3">${bet.outcomeType}</td>
                        <td colspan="3">${requestScope.sport_map[bet].name}
                            - ${requestScope.category_map[bet].name}</td>
                    </tr>
                    <tr>
                        <td colspan="3">${requestScope.event_map[bet].participant1}
                            - ${requestScope.event_map[bet].participant2}</td>
                    </tr>
                    <tr>
                        <td>${requestScope.event_map[bet].result1}
                            - ${requestScope.event_map[bet].result2}</td>
                        <c:set var="date" value="${requestScope.event_map[bet].date}"/>
                        <td>${j:formatDateTimeFromString(date, "MM.dd.yyyy HH:mm")}</td>
                        <td>${requestScope.event_map[bet].id}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </section>
</main>