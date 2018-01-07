<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main class="row container">
    <section class="section-center">
        <div class="section-header"><h2>Введите сумму ставки</h2></div>
        <div class="section-content">
            <table class="events">
                <tr>
                    <th>Категория</th>
                    <th>№ события</th>
                    <th>Дата</th>
                    <th>Событие</th>
                    <th>Тип исхода</th>
                    <th>Коэффициент</th>
                </tr>
                <tr>
                    <td>${requestScope.sportCategory.name} - ${requestScope.category.name}</td>
                    <td>${requestScope.event.id}</td>
                    <td>${requestScope.event.date}</td>
                    <td>${requestScope.event.participant1} - ${requestScope.event.participant2}</td>
                    <td>${requestScope.outcome.type}</td>
                    <td>${requestScope.outcome.coefficient}</td>
                </tr>
            </table>
            <div class="div-align-center">
                <form class="make-bet-form" action="controller" method="post">
                    <input type="hidden" name="command_type" value="make_bet"/>
                    <input type="hidden" name="event_id" value="${requestScope.event.id}"/>
                    <input type="hidden" name="outcome_type" value="${requestScope.outcome.type}"/>
                    <input type="hidden" name="outcome_coefficient" value="${requestScope.outcome.coefficient}"/>
                    <input type="number" name="bet_amount" value=""
                           title="Введите сумму ставки"
                           min="0.01" max="999.99" step="0.01" required/><br>
                    <input type="submit" value="Сделать ставку"/>
                </form>
            </div>
        </div>
    </section>
</main>