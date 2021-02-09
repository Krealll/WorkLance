<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${sessionScope.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle scope="session" basename="properties.language"/>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Employee</title>
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css?h=8fbddb265b84cd88828a6b52211b1304">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/assets/css/smoothproducts.css?h=38a515b6b30123300b3619cce6411cec">
    <link rel="icon" href="/img/fav.png" type="image/icon">
</head>

<body>
<c:import url="/jsp/parts/header.jsp"/>
<main class="page faq-page">
    <section class="clean-block clean-faq dark">
        <div class="container">
            <c:if test="${requestScope.chosen_user.id eq sessionScope.user.id}">
                <div class="block-heading">
                    <h1 class="text-info" style="margin-top: 20px">
                        <fmt:message key="profile.title"/>
                    </h1>
                </div>
            </c:if>
            <div align="center">
                <img src="/img/profile.png" width="200" height="200"
                     <c:choose>
                        <c:when test="${requestScope.chosen_user.id eq sessionScope.user.id}">
                            style="margin-top: 20px; margin-bottom: 20px"
                        </c:when>
                        <c:when test="${requestScope.chosen_user.id != sessionScope.user.id}">
                             style="margin-top: 120px;margin-bottom: 20px"
                        </c:when>
                    </c:choose>>
            </div>
                <div align="center">
                    <h2 class="text-info text-center"
                        <c:if test="${requestScope.chosen_user.id != sessionScope.user.id}">
                            style="margin-top: 20px"</c:if>>
                        ${requestScope.chosen_user.login}
                    </h2>
                </div>
                <c:if test="${requestScope.chosen_user.id eq sessionScope.user.id}">
                <div>
                    <div class="row">
                        <div class="col">
                            <label>
                                <fmt:message key="employee.email"/>
                            </label>
                            <label style="margin-left: 20px;">
                                    ${requestScope.chosen_user.email}
                            </label></div>
                    </div>
                </div>
            </c:if>
            <c:if test="${requestScope.chosen_user.id != sessionScope.user.id}">
                <c:if test="${requestScope.chosen_user.showEmail eq true}">
                    <div>
                        <div class="row">
                            <div class="col">
                                <label>
                                    <fmt:message key="employee.email"/>
                                </label>
                                <label style="margin-left: 20px;">
                                        ${requestScope.chosen_user.email}
                                </label></div>
                        </div>
                    </div>
                </c:if>
            </c:if>
            <div>
                <div class="row">
                    <div class="col">
                        <label>
                            <fmt:message key="employee.specialization"/>
                        </label>
                        <label style="margin-left: 20px;">
                            <ctg:specialization currentUser="${requestScope.chosen_user}"/>
                        </label>
                    </div>
                </div>
            </div>
            <div class="block-content">
                <div class="faq-item">
                    <h4 class="question">
                        <fmt:message key="employee.description"/>
                    </h4>
                    <div class="answer">
                        <p>
                            <c:if test="${requestScope.chosen_user.description eq null}">
                                <fmt:message key="employee.noInfo"/>
                            </c:if>
                            <c:if test="${requestScope.chosen_user.description != null}">
                                ${requestScope.chosen_user.description}
                            </c:if>
                        </p>
                    </div>
                </div>
            </div>

            <c:if test="${requestScope.chosen_user.id eq sessionScope.user.id}">
                <div class="row text-center" style="margin-top: 20px;">
                    <div class="col">
                        <button class="btn btn-primary" type="button"
                                onclick="location.href='projectServlet?user_id=${sessionScope.user.id}&command=to_user_orders_page'">
                            <fmt:message key="profile.button.orders"/>
                        </button>
                    </div>
                    <c:if test="${sessionScope.user.role eq 'USER'}">
                        <c:if test="${sessionScope.user.team == 0 || sessionScope.user.team eq null}">
                            <div class="col">
                                <button class="btn btn-primary" type="button"
                                        onclick="location.href='projectServlet?command=to_create_team_page'">
                                    <fmt:message key="profile.button.create"/>
                                </button>
                            </div>
                        </c:if>
                        <c:if test="${sessionScope.user.team != 0&&sessionScope.user.team != null}">
                            <div class="col">
                                <button class="btn btn-primary" type="button" onclick="location.href
                                        ='projectServlet?team_id=${requestScope.chosen_user.team}&command=find_team_by_id'">
                                    <fmt:message key="profile.button.team"/>
                                </button>
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${sessionScope.user.role eq 'MANAGER'}">
                        <div class="col">
                            <button class="btn btn-primary" type="button" onclick="location.href
                                    ='projectServlet?team_id=${requestScope.chosen_user.team}&command=find_team_by_id'">
                                <fmt:message key="profile.button.team"/>
                            </button>
                        </div>
                    </c:if>
                    <div class="col">
                        <button class="btn btn-primary" type="button"
                                onclick="location.href='projectServlet?command=to_notifications_page'">
                            <fmt:message key="profile.button.notifications"/>
                        </button>
                    </div>
                    <div class="col">
                        <button class="btn btn-primary" type="button"
                                onclick="location.href='projectServlet?command=to_edit_profile_page'">
                            <fmt:message key="profile.button.edit"/>
                        </button>
                    </div>
                </div>
            </c:if>
        </div>
    </section>
</main>
<c:import url="/jsp/parts/footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
<script src="/assets/js/smoothproducts.min.js?h=975b2635c9a06eae9f47d6cae8158a12"></script>
<script src="/assets/js/theme.js?h=417b03f5f0a4f9f27f8c248f532eb5af"></script>
</body>

</html>