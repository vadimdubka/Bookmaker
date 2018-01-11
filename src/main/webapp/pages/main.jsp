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

    <c:choose>
        <c:when test="${requestScope.event_set!=null}">
            <%@include file="jspf/events.jspf" %>
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