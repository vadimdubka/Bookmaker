<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--TODO удалить или доделать--%>
<main class="row container">
    <section class="section-center">
        <div class="section-header"><h2><fmt:message key="header.manage.players"/></h2></div>
        <div class="section-content">
            <table border="true">
                <tr>
                    <th><fmt:message key="player.name"/></th>
                    <th><fmt:message key="player.mname"/></th>
                    <th><fmt:message key="player.lname"/></th>
                </tr>
                <c:forEach var="player" items="${requestScope.players}">
                    <tr>
                        <td>${player.profile.firstName}</td>
                        <td>${player.profile.middleName}</td>
                        <td>${player.profile.lastName}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </section>
</main>