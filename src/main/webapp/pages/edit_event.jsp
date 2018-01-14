<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main class="row container">
    <section class="section-center col-s-10">
        <div class="section-header"><h2>Редактировать событие</h2></div>
        <div class="section-content">
            <table class="events">
                <tr>
                    <th>№</th>
                    <th>Дата</th>
                    <th>Событие</th>
                    <th>${requestScope.type_1_map.name}</th>
                    <th>${requestScope.type_x_map.name}</th>
                    <th>${requestScope.type_2_map.name}</th>
                    <th>Результат</th>
                    <th>Действие</th>
                </tr>
                <c:forEach var="event" items="${requestScope.event_set}">
                    <c:set var="event_id">${event.id}</c:set>
                    <tr>
                        <td>${event.id}</td>
                        <td>${j:formatDateTime(event.date, "dd.MM.yyyy HH:mm")}</td>
                        <td>${event.participant1} - ${event.participant2}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controller?command_type=goto_make_bet&event_id=${event.id}&outcome_type=${requestScope.type_1_map.name}">${requestScope.type_1_map[pageScope.event_id]}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controller?command_type=goto_make_bet&event_id=${event.id}&outcome_type=${requestScope.type_x_map.name}">${requestScope.type_x_map[pageScope.event_id]}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controller?command_type=goto_make_bet&event_id=${event.id}&outcome_type=${requestScope.type_2_map.name}">${requestScope.type_2_map[pageScope.event_id]}</a>
                        </td>
                        <td>${event.result1} - ${event.result2}</td>
                        <td>
                            <div class="custom-button button-edit button-edit-last"><a href="#">Удалить</a></div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </section>
</main>