<%@ page contentType="text/html;charset=UTF-8" %>
<main>
    <div class="layout-positioner">
        <section class="block-float border-test category">
            <div class="block-header">Виды спорта</div>
            <div class="block-content">
                <ul>
                    <%--TODO сделать через js and onclick, чтобы открывались категории по нажатию, а не по наведению--%>
                    <c:forEach var="sport" items="${requestScope.sport_set}">
                        <li>
                            <div class="sport-name">${sport.name}</div>
                            <ul>
                                <c:forEach var="category" items="${sport.childCategorySet}">
                                    <li>
                                        <div class="category-name">
                                            <a href="${pageContext.servletContext.contextPath}/controller?command_type=goto_main&category_id=${category.id}">${category.name}
                                            </a></div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </section>
        <c:choose>
            <c:when test="${requestScope.event_set!=null ||requestScope.event_set!=null}">
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
                            <c:forEach var="event" items="${requestScope.event_set}">
                                <tr>
                                    <td>${event.id}</td>
                                    <td>${event.date}</td>
                                    <td>${event.participant1} - ${event.participant2}</td>
                                    <td>1.36</td>
                                    <td>4.4</td>
                                    <td>11.5</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </section>
            </c:when>
            <c:otherwise>
                <section class="block-float border-test">
                    <div class="block-header">Новостной или рекламный блок</div>
                    <div class="block-content">
                        Новость или реклама
                    </div>
                </section>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${sessionScope.player != null}">
                <%@include file="jspf/player-menu.jsp" %>
            </c:when>
        </c:choose>
    </div>
</main>