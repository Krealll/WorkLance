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
    <title>Home</title>
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
<main class="page landing-page">
    <section class="clean-block slider dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info" style="margin-top: 20px"><fmt:message key="common.tagline"/></h2>
                <p>
                    <fmt:message key="home.design"/>
                </p>
            </div>

            <div class="carousel slide" data-ride="carousel" id="carousel-1">
                <div class="carousel-inner" role="listbox">
                    <div class="carousel-item active"><img class="w-100 d-block"
                               src="/assets/img/home_employee.jpg?h=105145bbc818c37fdfe323c6d62e598a"
                               alt="Slide Image" object-fit="cover"></div>
                    <div class="carousel-item"><img class="w-100 d-block"
                                src="/assets/img/home_order.jpg?h=3d6c10da1b7703da66371c9c07137ec6"
                                alt="Slide Image"></div>
                    <div class="carousel-item"><img class="w-100 d-block"
                                src="/assets/img/home_team.jpg?h=61cdcc055c7487a9f17f3f0a2584e149"
                                alt="Slide Image"></div>
                </div>
                <div>
                    <a class="carousel-control-prev" href="#carousel-1" role="button"
                     data-slide="prev"><span class="carousel-control-prev-icon"></span><span
                        class="sr-only">Previous</span></a>
                    <a class="carousel-control-next" href="#carousel-1" role="button"
                                       data-slide="next"><span class="carousel-control-next-icon"></span><span
                    class="sr-only">Next</span></a>
                </div>
                <ol class="carousel-indicators">
                    <li data-target="#carousel-1" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-1" data-slide-to="1"></li>
                    <li data-target="#carousel-1" data-slide-to="2"></li>
                </ol>
            </div>
        </div>
    </section>
    <c:if test="${sessionScope.user.role eq 'GUEST'}">
        <section class="clean-block clean-info dark">
            <div class="container">
                <div class="block-heading">
                    <h2 class="text-info">
                        <fmt:message key="home.startNow.name"/>
                    </h2>
                    <p>
                        <fmt:message key="home.startNow.description"/>
                    </p>
                    <button class="btn btn-primary btn-lg" type="button" style="margin: 0px;margin-top: 30px;"
                            onclick='location.href="projectServlet?command=to_register_page"'>
                        <fmt:message key="home.startNow.button"/>
                    </button>
                </div>
            </div>
        </section>
    </c:if>

    <section class="clean-block features">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info">
                    <fmt:message key="home.features.name"/>
                </h2>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-5 feature-box"><i class="icon-star icon"></i>
                    <h4>
                        <fmt:message key="home.features.find.name"/>
                    </h4>
                    <p>
                        <fmt:message key="home.features.find.description"/>
                    </p>
                </div>
                <div class="col-md-5 feature-box"><i class="icon-pencil icon"></i>
                    <h4>
                        <fmt:message key="home.features.tell.name"/>
                    </h4>
                    <p>
                        <fmt:message key="home.features.tell.description"/>
                    </p>
                </div>
                <div class="col-md-5 feature-box"><i class="icon-people icon"></i>
                    <h4>
                        <fmt:message key="home.features.team.name"/>
                    </h4>
                    <p>
                        <fmt:message key="home.features.team.description"/>
                    </p>
                </div>
                <div class="col-md-5 feature-box"><i class="icon-briefcase icon"></i>
                    <h4>
                        <fmt:message key="home.features.order.name"/>
                    </h4>
                    <p>
                        <fmt:message key="home.features.order.description"/>
                    </p>
                </div>
            </div>
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