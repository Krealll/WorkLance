<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${sessionScope.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle scope="session" basename="properties.language"/>
<html>

<head>
    <script type="text/javascript">
        window.history.forward();
        function noBack()
        {
            window.history.forward();
        }
    </script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Notifications</title>
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css?h=8fbddb265b84cd88828a6b52211b1304">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/assets/css/smoothproducts.css?h=38a515b6b30123300b3619cce6411cec">
    <link rel="icon" href="/img/fav.png" type="image/icon">
</head>

<body onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<c:import url="parts/header.jsp"/>
<main class="page faq-page">
    <section class="clean-block clean-faq dark">
        <div class="container">
            <div class="block-heading">
                <h1 class="text-info" style="margin-top: 20px">
                    <fmt:message key="notifications.name"/>
                </h1>
            </div>
            <c:if test="${requestScope.notifications_amount > 0}">
                <ctg:notification_pagination
                        userRole="${sessionScope.user.role}" pageNumber="${requestScope.current_page_number}"
                        notificationsOnPage="${requestScope.elements_on_page}"/>
            </c:if>
            <c:if test="${requestScope.notifications_amount==0}">
                <div>
                    <h2 style="color: black; margin-top: 10%;margin-left: 10%">
                        <fmt:message key="notifications.noNotifications"/>
                    </h2>
                </div>
            </c:if>

            <div class="col d-flex justify-content-center">
                <c:if test="${requestScope.notifications_amount > requestScope.elements_on_page}">
                    <nav>
                        <ul class="pagination">
                            <c:if test="${requestScope.current_page_number > 1}">
                                <li class="page-item">
                                    <a class="page-link" href=
                           "projectServlet?command=notification_pagination&current_page_number=${requestScope.current_page_number - 1}"
                                       aria-label="Previous"><span
                                            aria-hidden="true">«</span></a></li>
                            </c:if>
                            <li class="page-item active disabled"><a
                                    class="page-link">${current_page_number}</a></li>
                            <c:if test="${current_page_number < pages_amount}">
                                <li class="page-item">
                                    <a class="page-link" href=
                            "projectServlet?command=notification_pagination&current_page_number=${requestScope.current_page_number + 1}"
                                       aria-label="Next"><span aria-hidden="true">»</span></a></li>
                            </c:if>
                        </ul>
                    </nav>
                </c:if>
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