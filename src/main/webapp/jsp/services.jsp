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
    <title>Services</title>
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
<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info" style="margin-top: 20px">
                    <fmt:message key="services.title"/>
                </h2>
            </div>
            <div class="row align-items-center">
                <div class="col-md-6">
                    <img class="img-thumbnail" src="/assets/img/serv_employee.jpg?h=a354a996b1f796ed67472ce046e76d46">
                </div>
                <div class="col-md-6">
                    <h3>
                        <fmt:message key="services.employees.title"/>
                    </h3>
                    <div class="getting-started-info">
                        <p>
                            <fmt:message key="services.employees.body"/>
                        </p>
                    </div>
                    <button class="btn btn-primary btn-lg" type="button"
                            onclick="location.href='projectServlet?command=to_employees_page'">
                        <fmt:message key="services.employees.button"/>
                    </button>
                </div>
            </div>
            <div class="row align-items-center">
                <div class="col-md-6">
                    <img class="img-thumbnail" src="/assets/img/serv_order.jpg?h=ec6cf4663263942b0425f729f845a1bc">
                </div>
                <div class="col-md-6">
                    <h3>
                        <fmt:message key="services.order.title"/>
                    </h3>
                    <div class="getting-started-info">
                        <p>
                            <fmt:message key="services.order.body"/>
                        </p>
                    </div>
                    <div class="row">
                        <div class="col">

                            <button class="btn btn-primary btn-lg" type="button"
                                    onclick="location.href='projectServlet?command=to_orders_page'">
                                <fmt:message key="services.order.button.orders"/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row align-items-center">
                <div class="col-md-6">
                    <img class="img-thumbnail" src="/assets/img/serv_team.jpg?h=4552dfbc021edf65c56f18150fb867fa">
                </div>
                <div class="col-md-6">
                    <h3>
                        <fmt:message key="services.team.title"/>
                    </h3>
                    <div class="getting-started-info">
                        <p>
                            <fmt:message key="services.team.body"/>
                            <br><br></p>
                    </div>
                    <button class="btn btn-primary btn-lg" type="button"
                            onclick="location.href='projectServlet?command=to_teams_page'">
                        <fmt:message key="services.team.button"/>
                    </button>
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