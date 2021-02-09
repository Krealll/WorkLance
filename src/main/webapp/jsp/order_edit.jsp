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
    <title>Order edit</title>
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
                    <fmt:message key="order.edit.title"/>
                </h2>
            </div>
            <form method="post" action="projectServlet">
                <input type="hidden" name="command" value="edit_order"/>
                <div class="form-group">
                    <div class="col"><label>
                        <fmt:message key="order.name"/>
                    </label>
                        <input class="form-control" type="text" required name="name" id="name" placeholder=
                                "<fmt:message key="order.placeholder.name"/>"
                               value="${sessionScope.chosen_order.name}"
                                minlength="1" maxlength="20" pattern="[a-zA-Z][a-zA-Z0-9_]{1,29}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col">
                        <label>
                            <fmt:message key="order.budget"/>
                        </label>
                        <input class="form-control" type="text" required name="budget" id="budget" placeholder=
                                    "<fmt:message key="order.placeholder.budget"/>"
                               value="${sessionScope.chosen_order.budget}"
                                    pattern="\d+\.\d+" minlength="1" maxlength="20">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col">
                        <label>
                            <fmt:message key="status.name"/>
                        </label>
                        <select class="form-control" required name="status" id="status">
                        <optgroup label=<fmt:message key="status.choose"/>>
                            <option value="opened"
                                    <c:if test="${sessionScope.chosen_order.status=='OPENED'}">
                                        selected=""
                                    </c:if>>
                                <fmt:message key="status.opened"/>
                            </option>
                            <option value="in progress"
                                    <c:if test="${sessionScope.chosen_order.status=='IN_PROGRESS'}">
                                        selected=""
                                    </c:if>>
                                <fmt:message key="status.inprogress"/>
                            </option>
                            <option value="closed"
                                    <c:if test="${sessionScope.chosen_order.status=='CLOSED'}">
                                        selected=""
                                    </c:if>>
                                <fmt:message key="status.closed"/>
                            </option>
                        </optgroup>
                    </select>
                        <div class="form-row" style="margin-top: 20px;">
                            <div class="col">
                                <input type="checkbox" name="show" id="show" value="true"
                                <c:if test="${sessionScope.user.showEmail eq true}">
                                       checked
                                </c:if>>
                                <label style="margin-left: 20px;">
                                    <fmt:message key="order.contact.checkbox"/>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col">
                        <label>
                            <fmt:message key="order.description"/>
                        </label>
                        <textarea class="form-control" required name="description" id="description" placeholder=
                                    "<fmt:message key="order.placeholder.description"/>" minlength="20" maxlength="255"
                                  pattern="[\p{L}0-9!?%#&*+,.():\s-_]">${sessionScope.chosen_order.description}</textarea>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col text-center">
                        <button class="btn btn-primary text-center" type="submit">
                            <fmt:message key="order.edit.button"/>
                        </button>
                    </div>
                </div>
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