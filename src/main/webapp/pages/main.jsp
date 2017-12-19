<%@ page contentType="text/html;charset=UTF-8" %>
<main>
    <div class="layout-positioner">
        <section class="events-category layout-border">
            <div class="events-category-header">Виды спорта</div>
            <div class="events-category-content">
                <ul>
                    <c:forEach var="sport" items="${sessionScope.sport_list}">
                        <li>${sport.name}</li>
                        <ul>
                            <c:forEach var="category" items="${sport.childCategorySet}">
                                <li><i>${category.name}</i></li>
                            </c:forEach>
                        </ul>
                    </c:forEach>
                </ul>
            </div>
        </section>
        <section class="events layout-border">
            <table>
                <caption>Спортивные события</caption>
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
                <tr>
                    <td>2</td>
                    <td>15/06 19:00</td>
                    <td>Египет Уругвай</td>
                    <td>5.1</td>
                    <td>3.85</td>
                    <td>1.68</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>16/06 19:00</td>
                    <td>Португалия Испания</td>
                    <td>4.5</td>
                    <td>3.4</td>
                    <td>1.86</td>
                </tr>
            </table>
        </section>
        <section class="main-right-block layout-border">
            <c:choose>
                <c:when test="${sessionScope.player != null}">
                    <%@include file="jspf/player-menu.jsp" %>
                </c:when>
            </c:choose>
        </section>
    </div>
</main>