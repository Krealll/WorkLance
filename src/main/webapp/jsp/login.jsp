<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${sessionScope.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle scope="session" basename="properties.language"/>


<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login</title>
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css?h=8fbddb265b84cd88828a6b52211b1304">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/assets/css/smoothproducts.css?h=38a515b6b30123300b3619cce6411cec">
    <link rel="icon" href="/img/fav.png" type="image/icon">

</head>

<body <c:if test="${requestScope.something_wrong=='something_wrong'}">
onload=alert("${requestScope.something_wrong}");
</c:if>>
<c:import url="parts/header.jsp"/>
<main class="page login-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info" style="margin-top: 20px">
                    <fmt:message key="login.name"/>
                </h2>
            </div>
            <form method="post" action="projectServlet">
                <input type="hidden" name="command" value="login"/>
                <div class="form-group">
                    <label for="login">
                        <fmt:message key="login.field"/>
                    </label>
                    <input class="form-control" name="login" id="login" placeholder=
                            "<fmt:message key="login.placeholder.login"/>" required
                           minlength="1" maxlength="20" pattern="[a-zA-Z][a-zA-Z0-9._-]{1,19}"
                           <c:if test="${requestScope.something_wrong=='something_wrong'}">
                           value="${requestScope.login}"
                            </c:if>>
                </div>
                <div class="form-group">
                    <label for="password">
                        <fmt:message key="login.password"/>
                    </label>
                    <input class="form-control" type="password" id="password"
                           name="password" id="password" placeholder=
                                     "<fmt:message key="login.placeholder.password"/>" required
                             minlength="8" maxlength="25" pattern="[a-zA-Z\d_-]{8,25}"
                    <c:if test="${requestScope.something_wrong=='something_wrong'}">
                           value="${requestScope.password}"
                    </c:if>>
                </div>

                <div class="form-group">
                    <div class="form-check"><input class="form-check-input" type="checkbox" name="remember_me"
                                                   id="checkbox" value="true">
                        <label class="form-check-label" for="checkbox">
                            <fmt:message key="login.rememberMe"/>
                        </label>
                    </div>
                </div>
                <button class="btn btn-primary btn-block" type="submit">
                    <fmt:message key="login.name"/>
                </button>
            </form>
        </div>
    </section>
</main>
<c:import url="parts/footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
<script src="/assets/js/smoothproducts.min.js?h=975b2635c9a06eae9f47d6cae8158a12"></script>
<script src="/assets/js/theme.js?h=417b03f5f0a4f9f27f8c248f532eb5af"></script>
</body>
</html>