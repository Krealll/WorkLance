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
    <title>Order</title>
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
                <h2 class="text-info" style="margin-top: 20px">
                    ${requestScope.chosen_order.name}
                </h2>
            </div>
            <div>
                <div class="row">
                    <div class="col">
                        <label>
                            <fmt:message key="order.budget"/>
                        </label>
                        <label style="margin-left: 20px;">
                            ${requestScope.chosen_order.budget}
                        </label></div>
                </div>
            </div>
            <div>
                <div class="row">
                    <div class="col">
                        <label>
                            <fmt:message key="status.name"/>
                        </label>
                        <label style="margin-left: 20px;">
                            <ctg:order_status currentOrder="${requestScope.chosen_order}"/>
                        </label>
                    </div>
                </div>
            </div>
            <div>
                <div class="row">
                    <div class="col">
                        <label>
                            <fmt:message key="order.date"/>
                        </label>
                        <label style="margin-left: 20px;">
                            ${requestScope.chosen_order.date}
                        </label>
                    </div>
                </div>
            </div>
            <c:if test="${requestScope.email_allowed eq true}">
                <div>
                    <div class="row">
                        <div class="col">
                            <label>
                                <fmt:message key="order.contact"/>
                            </label>
                            <label style="margin-left: 20px;">
                                    ${requestScope.customer_email}
                            </label>
                        </div>
                    </div>
                </div>
            </c:if>
            <div class="block-content">
                <div class="faq-item">
                    <h4 class="question">
                        <fmt:message key="order.description"/>
                    </h4>
                    <div class="answer">
                        <p>
                            ${requestScope.chosen_order.description}
                        </p>
                    </div>
                </div>
            </div>
            <c:if test="${requestScope.chosen_order.customer eq sessionScope.user.id}">
                <div class="row text-center" style="margin-top: 20px;">
                    <div class="col">
                        <button class="btn btn-primary" type="button"
                                onclick="location.href='projectServlet?order_id=${requestScope.chosen_order.id}&command=to_edit_order_page'">
                            <fmt:message key="order.edit"/>
                        </button>
                    </div>
                    <div class="col">
                        <button class="btn btn-secondary" type="button"
                                onclick="location.href='projectServlet?order_id=${requestScope.chosen_order.id}&command=delete_order'">
                            <fmt:message key="order.delete"/>
                        </button>
                    </div>
                </div>
            </c:if>
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