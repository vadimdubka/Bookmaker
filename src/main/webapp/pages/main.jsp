<%@ page contentType="text/html;charset=UTF-8" %>
<main class="row container">

    <c:choose>
        <c:when test="${sessionScope.user == null}">
            <%@include file="jspf/user-login.jspf" %>
        </c:when>
        <c:otherwise>
            <%@include file="jspf/user-menu.jspf" %>
        </c:otherwise>
    </c:choose>

    <%@include file="jspf/sport-category.jspf" %>

    <c:set var="event_command_type" value="${sessionScope.event_command_type}" scope="page"/>
    <c:choose>
        <c:when test="${requestScope.event_set!=null && event_command_type!=null}">
            <c:choose>
                <c:when test="${event_command_type == 'show_actual'}">
                    <%@include file="jspf/events_actual.jspf" %>
                </c:when>
                <c:when test="${event_command_type == 'manage'}">
                    <%@include file="jspf/events_actual.jspf" %>
                </c:when>
                <c:when test="${event_command_type == 'set_coefficient'}">
                    <%@include file="jspf/events_actual.jspf" %>
                </c:when>
                <c:when test="${event_command_type == 'correct_coefficient'}">
                    <%@include file="jspf/events_actual.jspf" %>
                </c:when>
                <c:when test="${event_command_type == 'set_result'}">
                    <%@include file="jspf/events_actual.jspf" %>
                </c:when>
                <c:when test="${event_command_type == 'show_result'}">
                    <%@include file="jspf/events_actual.jspf" %>
                </c:when>
                <c:when test="${event_command_type == 'manage_failed'}">
                    <%@include file="jspf/events_actual.jspf" %>
                </c:when>
                <c:otherwise>
                    <%@include file="jspf/events_actual.jspf" %>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <section class="section-promo col-s-12 col-6 col-float-right">
                <div class="section-header"><h2>Выбери свой спорт!</h2></div>
                <img class="img-choose-sport"
                     src="${pageContext.request.contextPath}/resources/img/choose-sport.jpg"
                     alt="Choose-sport-logo"
                     title="Choose your sport">
            </section>
        </c:otherwise>
    </c:choose>
</main>