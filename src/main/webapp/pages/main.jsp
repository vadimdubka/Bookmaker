<%@ page contentType="text/html;charset=UTF-8" %>
<main>
    <div class="layout-positioner">
        <section class="block-float border-test category">
            <div class="block-header">Виды спорта</div>
            <div class="block-content">
                <ul>
                    <c:forEach var="sport" items="${sessionScope.sport_list}">
                        <li>
                            <div class="sport-name">${sport.name}</div>
                            <ul>
                                <c:forEach var="category" items="${sport.childCategorySet}">
                                    <li>
                                        <div class="category-name">${category.name}</div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </section>
        <section class="block-float border-test events">
            <div class="block-header">Спортивные события</div>
            <div class="block-content">
                <table>
                    <tr>
                        <th>№</th>
                        <th>Дата</th>
                        <th>Событие</th>
                        <th>1</th>
                        <th>X</th>
                        <th>2</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>14/06 19:00</td>
                        <td>Россия Саудовская Аравия</td>
                        <td>1.36</td>
                        <td>4.4</td>
                        <td>11.5</td>
                    </tr>
                </table>
            </div>

        </section>
        <section class="block-float border-test player ">
            <div class="block-header">Аккаунт игрока</div>
            <div class="block-content">
                <c:choose>
                    <c:when test="${sessionScope.player != null}">
                        <%@include file="jspf/player-menu.jsp" %>
                    </c:when>
                </c:choose>
            </div>
        </section>
    </div>
</main>