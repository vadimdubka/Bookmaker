<%@ page contentType="text/html;charset=UTF-8" %>
<main>
    <div class="layout-positioner">
        <section class="block-float border-test">
            <div class="section-header"><h2><fmt:message key="register.header"/></h2></div>
            <div class="section-content">
                <noscript><p class="error-message">${requestScope.errorMessage}</p></noscript>
                <form class="register-form clearfix" name="registerForm" action="controller" method="post">
                    <input type="hidden" name="command_type" value="register"/>
                    <div class="input-block">
                        <label class="required" for="email-input"><fmt:message key="register.email"/></label>
                        <input id="email-input" type="email" name="email" value=""
                               pattern="^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$"
                               title="<fmt:message key="register.email.title"/>"
                               maxlength="320" required/>
                    </div>
                    <div class="input-block">
                        <label class="required" for="password-input"><fmt:message key="register.password"/></label>
                        <input id="password-input" type="password" name="password" value=""
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[\w_-]{8,}$"
                               title="<fmt:message key="register.password.title"/>"
                               required/>
                    </div>
                    <div class="input-block">
                        <label class="required" for="password-input-again"><fmt:message
                                key="register.passwordagain"/></label>
                        <input id="password-input-again" type="password" name="password_again" value=""
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[\w_-]{8,}$"
                               title="<fmt:message key="register.password.title"/>"
                               required/>
                    </div>
                    <div class="input-block">
                        <label for="lname-input"><fmt:message key="register.lname"/></label>
                        <input id="lname-input" type="text" name="lname" value="" pattern="[A-Za-z ]{1,70}"
                               title="<fmt:message key="register.lname.title"/>" required/>
                    </div>
                    <div class="input-block">
                        <label for="fname=input"><fmt:message key="register.fname"/></label>
                        <input id="fname=input" type="text" name="fname" value="" pattern="[A-Za-z ]{1,70}"
                               title="<fmt:message key="register.fname.title"/>" required/>
                    </div>
                    <div class="input-block">
                        <label for="mname-input"><fmt:message key="register.mname"/></label>
                        <input id="mname-input" type="text" name="mname" value="" pattern="[A-Za-z ]{1,70}"
                               title="<fmt:message key="register.mname.title"/>" required/>
                    </div>
                    <div class="input-block">
                        <label class="required" for="birthdate-input"><fmt:message key="register.birthdate"/></label>
                        <input id="birthdate-input" type="date" name="birthday" value=""
                               min="1900-01-01" title="<fmt:message key="register.birthdate.title"/>" required/>
                    </div>
                    <div class="input-block">
                        <input type="submit" value="<fmt:message key="register.submit"/>"/>
                    </div>
                </form>
                <div class="custom-link">
                    <a href="${pageContext.request.contextPath}/controller?command_type=goto_index">
                        <fmt:message key="register.back"/>
                    </a>
                </div>
            </div>
        </section>
    </div>
</main>


