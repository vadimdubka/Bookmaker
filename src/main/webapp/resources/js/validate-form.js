function validateRegister() {
    var result = true;

    var PATTERN_PASSWORD = "^(?=\\w*[0-9])(?=\\w*[a-z])(?=\\w*[A-Z])(?=\\S+$)\\w{6,}$",
        PATTERN_NAME = "^[A-Za-z]\\w{4,70}$";

    var ERR_EMPTY_FIELD = "Заполните поле",
        ERR_EMAIL_PATTERN = "Email должен содержать . и @, например: 'ivanov.ivan@gmail.com'",
        ERR_PASSWORD_NOT_MATCH = "Пароли не совпадают",
        ERR_PASSWORD_PATTERN = "Пароль должен состоять только из латинских букв и цифр, содержать не менее 6 символов, в т.ч. 1 заглавную букву, 1 строчную букву, 1 цифру",
        ERR_BDATE_MORE = "Максимальный возраст - 120 лет",
        ERR_BDATE_LESS = "Вам нет 18 лет",
        ERR_NAME_PATTERN = "Поле должно состоять только из латинских букв, цифр и _ , начинаться с буквы, содержать не менее 5 символов";

    var errEmail = document.getElementById("err-email"),
        errPwd1 = document.getElementById("err-pwd1"),
        errPwd2 = document.getElementById("err-pwd2"),
        errBdate = document.getElementById("err-bdate"),
        errFname = document.getElementById("err-fname"),
        errMname = document.getElementById("err-mname"),
        errLname = document.getElementById("err-lname");

    var form = document.registerForm,
        email = form.email.value,
        pwd1 = form.password.value,
        pwd2 = form.password_again.value,
        bdate = form.birthdate.value,
        fname = form.fname.value,
        mname = form.mname.value,
        lname = form.lname.value;

    errEmail.innerHTML = "";
    errPwd1.innerHTML = "";
    errPwd2.innerHTML = "";
    errBdate.innerHTML = "";
    errFname.innerHTML = "";
    errMname.innerHTML = "";
    errLname.innerHTML = "";

    if (!email) {
        errEmail.innerHTML = ERR_EMPTY_FIELD;
        result = false;
    }
    if (email && !(~email.indexOf(".") && ~email.indexOf("@"))) {
        errEmail.innerHTML = ERR_EMAIL_PATTERN;
        result = false;
    }
    if (!pwd1) {
        errPwd1.innerHTML = ERR_EMPTY_FIELD;
        result = false;
    }
    if (!pwd2) {
        errPwd2.innerHTML = ERR_EMPTY_FIELD;
        result = false;
    }
    if (pwd1 && !~pwd1.search(PATTERN_PASSWORD)) {
        errPwd1.innerHTML = ERR_PASSWORD_PATTERN;
        form.password.value = "";
        form.password_again.value = "";
        result = false;
    }
    if (pwd1 && pwd2 && pwd1 !== pwd2) {
        errPwd1.innerHTML = ERR_PASSWORD_NOT_MATCH;
        errPwd2.innerHTML = ERR_PASSWORD_NOT_MATCH;
        form.password.value = "";
        form.password_again.value = "";
        result = false;
    }
    if (!bdate) {
        errBdate.innerHTML = ERR_EMPTY_FIELD;
        result = false;
    }
    if (bdate) {
        bdate = new Date(bdate);
        var year = bdate.getFullYear(),
            month = bdate.getMonth(),
            day = bdate.getDate(),
            curDate = new Date(),
            curYear = curDate.getFullYear(),
            curMonth = curDate.getMonth(),
            curDay = curDate.getDate(),
            delYear = curYear - year,
            delMonth = curMonth - month,
            delDay = curDay - day;

        /*Ограничение по возрасту увеличено с 7 до 18 лет, т.к. этого требует бизнес-логика приложения.*/
        if (!(delYear > 18
            || delYear == 18 && delMonth > 0
            || delYear == 18 && delMonth == 0 && delDay >= 0)) {
            errBdate.innerHTML = ERR_BDATE_LESS;
            result = false;
        }

        if (delYear > 120) {
            errBdate.innerHTML = ERR_BDATE_MORE;
            result = false;
        }
    }

    if (!fname) {
        errFname.innerHTML = ERR_EMPTY_FIELD;
        result = false;
    }
    if (fname && !~fname.search(PATTERN_NAME)) {
        errFname.innerHTML = ERR_NAME_PATTERN;
        result = false;
    }

    if (!mname) {
        errMname.innerHTML = ERR_EMPTY_FIELD;
        result = false;
    }
    if (mname && !~mname.search(PATTERN_NAME)) {
        errMname.innerHTML = ERR_NAME_PATTERN;
        result = false;
    }

    if (!lname) {
        errLname.innerHTML = ERR_EMPTY_FIELD;
        result = false;
    }
    if (lname && !~lname.search(PATTERN_NAME)) {
        errLname.innerHTML = ERR_NAME_PATTERN;
        result = false;
    }

    var emailInput2 = document.getElementById("email-input2");

    if (emailInput2) {
        var errEmail2 = document.getElementById("err-email2");
        var email2 = form.email2.value;
        errEmail2.innerHTML = "";
        if (!email2) {
            errEmail2.innerHTML = ERR_EMPTY_FIELD;
            result = false;
        }
        if (email2 && !(~email2.indexOf(".") && ~email2.indexOf("@"))) {
            errEmail2.innerHTML = ERR_EMAIL_PATTERN;
            result = false;
        }
    }
    return result;
}

function addEmail(a) {
    var buttonAddEmail = a;
    var emailBlockInner1 = buttonAddEmail.parentNode;
    emailBlockInner1.removeChild(buttonAddEmail);

    var label = document.createElement("label");
    label.innerHTML = "Дополнительный E-mail";
    label.setAttribute("for", "email-input2");
    label.setAttribute("class", "required");

    var span = document.createElement("span");
    span.setAttribute("id", "err-email2");
    span.setAttribute("class", "err-msg");

    var input = document.createElement("input");
    input.setAttribute("id", "email-input2");
    input.setAttribute("name", "email2");
    input.setAttribute("maxlength", "320");
    input.setAttribute("type", "email");
    input.setAttribute("required", "");

    var button = document.createElement("button");
    button.innerHTML = "Удалить";
    button.setAttribute("onclick", "return deleteEmail(this)");

    var emailBlockInner2 = document.createElement("div");
    emailBlockInner2.setAttribute("id", "email-block-inner2");
    emailBlockInner2.setAttribute("class", "input-block-inner");
    emailBlockInner2.appendChild(input);
    emailBlockInner2.appendChild(button);

    var emailBlock = document.getElementById("email-block");
    emailBlock.appendChild(label);
    emailBlock.appendChild(span);
    emailBlock.appendChild(emailBlockInner2);
}

function deleteEmail(a) {
    var button = document.createElement("button");
    button.innerHTML = "Добавить Email";
    button.setAttribute("onclick", "return addEmail(this)");

    var emailBlockInner1 = document.getElementById("email-block-inner1");
    emailBlockInner1.appendChild(button);

    var emailBlockInner2 = a.parentNode;
    var span = emailBlockInner2.previousSibling;
    var label = span.previousSibling;
    var emailBlock = emailBlockInner2.parentNode;
    emailBlock.removeChild(emailBlockInner2);
    emailBlock.removeChild(span);
    emailBlock.removeChild(label);
}
