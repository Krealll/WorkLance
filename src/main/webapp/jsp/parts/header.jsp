<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${sessionScope.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle scope="session" basename="properties.language"/>


<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css?h=8fbddb265b84cd88828a6b52211b1304">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/assets/css/smoothproducts.css?h=38a515b6b30123300b3619cce6411cec">
</head>
<body>
<nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
    <div class="container">
        <a class="navbar-brand logo"  style="cursor: pointer" onclick="location.href='projectServlet?command=to_home_page'">
            <fmt:message key="header.name"/>
        </a>
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1">
            <span class="sr-only">Toggle navigation</span><span
                class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse"
             id="navcol-1">
            <ul class="nav navbar-nav ml-auto">
            <c:choose>
                <c:when test="${sessionScope.user.role eq 'USER'||sessionScope.user.role eq 'MANAGER' }">
                    <li class="nav-item" role="presentation">
                        <a class="nav-link" href="/projectServlet?command=to_profile_page">
                            <fmt:message key="header.profile"/>
                        </a>
                    </li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link" href="/projectServlet?command=logout">
                            <fmt:message key="header.logout"/>
                        </a>
                    </li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link" href="/projectServlet?command=to_create_order_page">
                            <fmt:message key="services.order.button.create"/>
                        </a>
                    </li>
                </c:when>
                <c:when test="${sessionScope.user.role eq 'GUEST'}">
                    <li class="nav-item" role="presentation">
                        <a class="nav-link" href="/projectServlet?command=to_register_page">
                            <fmt:message key="header.sign_up"/>
                        </a>
                    </li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link" href="/projectServlet?command=to_login_page">
                            <fmt:message key="header.log_in"/>
                        </a>
                    </li>
                </c:when>
            </c:choose>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" href="/projectServlet?command=to_services_page">
                        <fmt:message key="header.services"/>
                    </a>
                </li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" style="color: rgba(0,0,0,0.97); cursor: pointer;"
                       onclick='location.href = "projectServlet?command=change_language"'>
                        ${sessionScope.locale=="ru_RU"?"RUS":"EN"}
                    </a>
                </li>

            </ul>
        </div>
    </div>
</nav>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
<script src="/assets/js/smoothproducts.min.js?h=975b2635c9a06eae9f47d6cae8158a12"></script>
<script src="/assets/js/theme.js?h=417b03f5f0a4f9f27f8c248f532eb5af"></script>
</body>
</html>