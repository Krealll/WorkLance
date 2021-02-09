<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${sessionScope.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle scope="session" basename="properties.language"/>
<html>

<head >
    <script type="text/javascript">
        window.history.forward();
        function noBack()
        {
            window.history.forward();
        }
    </script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Teams</title>
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css?h=8fbddb265b84cd88828a6b52211b1304">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/assets/css/smoothproducts.css?h=38a515b6b30123300b3619cce6411cec">
    <link rel="icon" href="/img/fav.png" type="image/icon">

</head>

<body onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="" >
<c:import url="parts/header.jsp"/>
<main class="page catalog-page">
    <section class="clean-block clean-catalog dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info" style="margin-top: 20px">
                   <fmt:message key="teams.title"/>
                </h2>
            </div>
            <div class="content">
                <div class="row">

                    <div class="col-md-3">
                        <div class="text-left filters">
                            <form class="text-center"  method="post" action="projectServlet" style="margin-bottom: 10px;">
                                <input type="hidden" name="command" value="find_teams_command"/>
                                <div class="form-group">
                                    <h3><strong>
                                        <fmt:message key="teams.search.name.title"/>
                                    </strong><br></h3>
                                    <input class="form-control"
                                          type="text" required name="name" id="name"
                                          placeholder=
                                                   "<fmt:message key="teams.search.name.placeholder"/>"
                                          minlength="1" maxlength="30"
                                          pattern="[a-zA-Z][a-zA-Z0-9_]{1,29}">
                                </div>
                                <button class="btn btn-primary text-center"
                                        type="submit">
                                    <fmt:message key="common.button.find"/>
                                </button>
                            </form>
                            <form class="text-center"  method="post" action="projectServlet" style="margin-bottom: 10px;">
                                <input type="hidden" name="command" value="find_teams_command"/>
                                <div class="form-group">
                                    <h3><strong>
                                        <fmt:message key="teams.search.manager.title"/>
                                    </strong><br></h3>
                                    <input class="form-control"
                                          type="text" required name="login" id="login"
                                          placeholder=
                                                   "<fmt:message key="teams.search.manager.placeholder"/>"
                                          minlength="1"
                                          maxlength="20"
                                          pattern="[a-zA-Z][a-zA-Z0-9._-]{1,19}">
                                </div>
                                <button class="btn btn-primary text-center"
                                        type="submit">
                                    <fmt:message key="common.button.find"/>
                                </button>
                            </form>
                            <form class="text-center"  method="post" action="projectServlet" style="margin-bottom: 10px;">
                                <input type="hidden" name="command" value="find_teams_command"/>
                                <div class="form-group">
                                    <h3><strong>
                                        <fmt:message key="teams.search.key.title"/>
                                    </strong><br></h3>
                                </div>
                                <input class="form-control" type="text" style="margin-bottom: 20px;" placeholder=
                                        "<fmt:message key="teams.search.key.placeholder"/>"
                                       required name="key" id="key"
                                       minlength="1" maxlength="30" pattern="[\p{L}0-9!?%#&*+,.():\s-_]">

                                <button class="btn btn-primary text-center" type="submit" >
                                    <fmt:message key="common.button.find"/>
                                </button>
                            </form>
                        </div>
                    </div>

                    <div class="col-md-9">
                        <div class="products">
                            <div class="row no-gutters" style="border-top: 0px; border-left: 0px">
                                <c:if test="${requestScope.number_of_teams>0}">
                                    <ctg:team_pagination pageNumber="${requestScope.current_page_number}"
                                                         teamsOnPage="${requestScope.elements_on_page}"/>

                                </c:if>
                                <c:if test="${requestScope.number_of_teams eq 0}">
                                    <div>
                                        <h1 style="color: black; margin-top: 10%;margin-left: 10%">
                                            <fmt:message key="teams.noTeams"/>
                                        </h1>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <c:if test="${requestScope.number_of_teams > requestScope.elements_on_page}">
                            <nav>
                                <ul class="pagination">
                                    <c:if test="${requestScope.current_page_number > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href=
                                "projectServlet?command=team_pagination&current_page_number=${requestScope.current_page_number  - 1}"
                                               aria-label="Previous"><span
                                                    aria-hidden="true">«</span></a></li>
                                    </c:if>
                                    <li class="page-item active disabled">
                                        <a class="page-link">
                                                ${requestScope.current_page_number }
                                        </a>
                                    </li>
                                    <c:if test="${requestScope.current_page_number  < requestScope.pages_amount }">
                                        <li class="page-item">
                                            <a class="page-link" href=
                                "projectServlet?command=team_pagination&current_page_number=${requestScope.current_page_number+ 1}"
                                               aria-label="Next">
                                                <span aria-hidden="true">»</span></a></li>
                                    </c:if>
                                </ul>
                            </nav>
                        </c:if>
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