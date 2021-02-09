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
    <title>Edit members</title>
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css?h=8fbddb265b84cd88828a6b52211b1304">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/assets/css/smoothproducts.css?h=38a515b6b30123300b3619cce6411cec">
    <link rel="icon" href="/img/fav.png" type="image/icon">

</head>

<body>
<c:import url="parts/header.jsp"/>
<main class="page faq-page">
    <section class="clean-block clean-faq dark">
        <div class="container">
            <div class="block-heading">
                <div class="col">
                    <div class="row" style="margin-top: 30px">
                        <div class="col">
                            <h3 class="text-info">
                                <fmt:message key="team.members"/>
                            </h3>
                            <c:if test="${requestScope.number_of_members>0}">
                                <ctg:members_pagination pageNumber="${requestScope.current_page_number}"
                                                        membersOnPage="${requestScope.elements_on_page}"/>
                            </c:if>
                            <c:if test="${requestScope.number_of_members eq 0}">
                                <div>
                                    <h4 style="color: black; margin-top: 10%;margin-left: 10%">
                                        <fmt:message key="team.edit.noMembers"/>
                                    </h4>
                                </div>
                            </c:if>
                            <c:if test="${requestScope.number_of_members > requestScope.elements_on_page}">
                                <div class="row" style="margin-bottom: 20px">
                                    <div class="col offset-4">
                                        <nav>
                                            <ul class="pagination">
                                                <c:if test="${requestScope.current_page_number > 1}">
                                                    <li class="page-item">
                                                        <a class="page-link" href=
                                            "projectServlet?command=member_pagination&current_page_number=${requestScope.current_page_number - 1}"
                                                           aria-label="Previous"><span
                                                                aria-hidden="true">«</span></a></li>
                                                </c:if>
                                                <li class="page-item active disabled"><a
                                                        class="page-link">${requestScope.current_page_number}</a></li>
                                                <c:if test="${requestScope.current_page_number < requestScope.pages_amount}">
                                                    <li class="page-item">
                                                        <a class="page-link" href=
                                            "projectServlet?command=member_pagination&current_page_number=${requestScope.current_page_number + 1}"
                                                           aria-label="Next"><span aria-hidden="true">»</span></a></li>
                                                </c:if>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div class="col">
                            <form method="post" action="projectServlet">
                                <input type="hidden" name="command" value="invite_user"/>
                                <div class="form-group text-left">
                                    <div class="col">
                                        <h3>
                                            <fmt:message key="team.edit.members.invitation"/>
                                        </h3>
                                        <input class="form-control"
                                               type="text" required name="login" id="login" placeholder=
                                                       "<fmt:message key="team.edit.placeholder.invitation"/>"
                                               style="margin-top: 10px;" minlength="1" maxlength="20"
                                               pattern="[a-zA-Z][a-zA-Z0-9._-]{1,19}">
                                        <button class="btn btn-primary"
                                                type="submit" style="margin-top: 20px;">
                                            <fmt:message key="team.edit.invite"/>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
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