<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <div class="layout-positioner">
        <table border="true">
            <tr>
                <th>Name</th>
                <th>Middle Name</th>
                <th>Last Name</th>
            </tr>
            <c:forEach var="player" items="${requestScope.players}">
                <jsp:useBean id="player" type="com.dubatovka.app.entity.Player" scope="page"/>
                <tr>
                    <td>${player.profile.firstName}</td>
                    <td>${player.profile.middleName}</td>
                    <td>${player.profile.lastName}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>