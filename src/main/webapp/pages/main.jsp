<%@ page contentType="text/html;charset=UTF-8" %>
<main>
    <div class="layout-positioner">
        <section class="block-float border-test category">
            <div class="section-header">Виды спорта</div>
            <div class="section-content">
                <ul>
                    <%--TODO сделать через js and onclick, чтобы открывались категории по нажатию, а не по наведению--%>
                    <c:forEach var="sport" items="${requestScope.sport_set}">
                        <li>
                            <div class="sport-name">${sport.name}</div>
                            <ul>
                                <c:forEach var="section-category" items="${sport.childCategorySet}">
                                    <li>
                                        <div class="category-name">
                                            <a href="${pageContext.request.contextPath}/controller?command_type=goto_main&category_id=${ section-category .id}">${ section-category .name}
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
            <c:when test="${requestScope.event_set!=null}">
                <section class="block-float border-test events">
                    <div class="section-header">Спортивные события</div>
                    <div class="section-content">
                        <table>
                            <tr>
                                <th>№</th>
                                <th>Дата</th>
                                <th>Событие</th>
                                <th>${requestScope.type_1_map.name}</th>
                                <th>${requestScope.type_x_map.name}</th>
                                <th>${requestScope.type_2_map.name}</th>
                            </tr>
                            <c:forEach var="event" items="${requestScope.event_set}">
                                <c:set var="event_id">${event.id}</c:set>
                                <tr>
                                    <td>${event.id}</td>
                                    <td>${event.date}</td>
                                    <td>${event.participant1} - ${event.participant2}</td>
                                    <td>${requestScope.type_1_map[pageScope.event_id]}</td>
                                    <td>${requestScope.type_x_map[pageScope.event_id]}</td>
                                    <td>${requestScope.type_2_map[pageScope.event_id]}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </section>
            </c:when>
            <c:otherwise>
                <section class="block-float border-test promo">
                    <div class="section-header">Выбери свой спорт!</div>
                    <div>
                        <img class="img-choose-sport" src="${pageContext.request.contextPath}/resources/img/choose-sport.jpg"
                             alt="Choose-sport-logo" title="Choose your sport">
                    </div>
                </section>
            </c:otherwise>
        </c:choose>
        <section class="block-user border-test">
            <c:choose>
                <c:when test="${sessionScope.user == null}">
                    <%@include file="jspf/user-login.jsp" %>
                </c:when>
                <c:otherwise>
                    <%@include file="jspf/user-menu.jsp" %>
                </c:otherwise>
            </c:choose>
        </section>
    </div>
</main>