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
                    <th>Участник 1</th>
                    <th>Участник 2</th>
                    <th>1</th>
                    <th>X</th>
                    <th>2</th>
                    <th>Результат 1</th>
                    <th>Результат 2</th>
                    <th>Действие</th>
                </tr>
                <tr>
                    <td>${requestScope.event.id}</td>
                    <td>${j:formatDateTime(requestScope.event.date, "dd.MM.yyyy HH:mm")}</td>
                    <td>${requestScope.event.participant1}</td>
                    <td>${requestScope.event.participant2}</td>
                    <td>${requestScope.outcome_map["1"].coefficient}</td>
                    <td>${requestScope.outcome_map["X"].coefficient} </td>
                    <td>${requestScope.outcome_map["2"].coefficient} </td>
                    <td>${requestScope.event.result1}</td>
                    <td>${requestScope.event.result2}</td>
                    <td>
                        <div class="custom-button button-edit button-edit-last"><a href="#">Сохранить</a></div>
                    </td>
                </tr>
                <tr>
                    <form class="edit-event-form" action="controller" method="post">
                        <input type="hidden" name="command_type" value="edit_event"/>
                        <input type="hidden" name="event_id" value="${requestScope.event.id}"/>
                        <td>${requestScope.event.id}</td>
                        <td><input type="datetime" name="date" value="${j:formatDateTime(requestScope.event.date, "dd.MM.yyyy HH:mm")}" min="2018-01-01" required/></td>
                        <td><input type="text" name="participant1" value="${requestScope.event.participant1}" pattern="[A-Za-z]{1,70}" required/></td>
                        <td><input type="text" name="participant2" value="${requestScope.event.participant2}" pattern="[A-Za-z]{1,70}" required/></td>
                        <td>
                            <input type="number" name="outcome_1" value="${requestScope.outcome_map["1"].coefficient}" title="Введите коэффициент" min="1" max="10" step="0.01" required/><br>
                        </td>
                        <td>
                            <input type="number" name="outcome_X" value="${requestScope.outcome_map["X"].coefficient}" title="Введите коэффициент" min="1" max="10" step="0.01" required/><br>
                        </td>
                        <td>
                            <input type="number" name="outcome_2" value="${requestScope.outcome_map["2"].coefficient}" title="Введите коэффициент" min="1" max="10" step="0.01" required/><br>
                        </td>
                        <td>
                            <input type="number" name="result_1" value="${requestScope.event.result1}" title="Введите результат" min="0" max="100" step="1" required/><br>
                        </td>
                        <td>
                            <input type="number" name="result_2" value="${requestScope.event.result2}" title="Введите результат" min="0" max="100" step="1" required/><br>
                        </td>
                        <td><input type="submit" value="Сохранить"/></td>
                    </form>
                </tr>
            </table>
        </div>
    </section>
</main>