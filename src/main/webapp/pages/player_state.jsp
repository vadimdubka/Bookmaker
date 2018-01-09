<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main class="row container">
    <section class="section-center">
        <div class="section-header"><h2>Введите сумму ставки</h2></div>
        <div class="section-content">
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
            <table class="events">
                <tr>
                    <th>Дата ставки</th>
                    <th>Статус ставки</th>
                    <th>Сумма ставки</th>
                    <th>Коэфф.</th>

                    <th>Категория события</th>
                    <th>Событие</th>
                    <th>Тип</th>
                    <th>Дата</th>
                    <th>№</th>
                </tr>
                <c:forEach var="bet_info_entry" items="${requestScope.bet_info_map}">
                    <tr>

                        <td>${bet_info_entry.key.date}</td>
                        <td>${bet_info_entry.key.status.status}</td>
                        <td>${bet_info_entry.key.amount}</td>
                        <td>${bet_info_entry.key.coefficient}</td>

                        <td>${bet_info_entry.value["sportCategory"].name}
                            - ${bet_info_entry.value["category"].name}</td>
                        <td>${bet_info_entry.value["event"].participant1}
                            - ${bet_info_entry.value["event"].participant2}</td>
                        <td>${bet_info_entry.key.outcomeType}</td>
                            <%--<jsp:useBean id="event" scope="request" class="com.dubatovka.app.entity.Event"/>--%>
                            <%--<td>${j:formatDateTime(event.date, "dd.MM.yyyy HH:mm")}</td>--%>
                        <td>${bet_info_entry.value["event"].date}</td>
                        <td>${bet_info_entry.value["event"].id}</td>

                    </tr>
                </c:forEach>
            </table>
        </div>
    </section>
</main>