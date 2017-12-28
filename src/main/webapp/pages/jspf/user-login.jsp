<%@ page contentType="text/html;charset=UTF-8" %>
<section class="block-float border-test">
    <div class="block-header">Войдите в систему</div>
    <div class="block-content">
        <form class="login" name="loginForm" action="controller" method="post">
            <input type="hidden" name="command_type" value="login"/>
            <input type="email" name="email" value=""
                   placeholder="<fmt:message key="login.email_holder"/>"
                   title="<fmt:message key="register.email.title"/>"
                   pattern="^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$"
                   maxlength="320" required/><br/>
            <input type="password" name="password" value=""
                   placeholder="<fmt:message key="login.password_holder"/>"
                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[\w_-]{8,}$"
                   title="<fmt:message key="register.password.title"/>"
                   required/><br/>
            <noscript><p class="error-message">${requestScope.errorMessage}</p></noscript>
            <input class="button-login" type="submit" value="<fmt:message key="login.submit"/>"/>
        </form>
        <div class="custom-link">
            <a href="${pageContext.request.contextPath}/controller?command_type=goto_register">
                <fmt:message key="login.register"/></a>
        </div>
    </div>
</section>