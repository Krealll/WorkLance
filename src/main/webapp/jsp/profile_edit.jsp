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
    <title>Profile editing</title>
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
                    <fmt:message key="profile.title"/>
                </h2>
            </div>
            <form method="post" action="projectServlet">
                <input type="hidden" name="command" value="edit_profile"/>
                <div class="form-group">
                    <div class="col"><label>
                        <fmt:message key="profile.login"/>
                    </label>
                        <input class="form-control" type="text" placeholder=
                                "<fmt:message key="profile.placeholder.login"/>"
                               value="${user.login}" required name="login" id="login"
                               minlength="1" maxlength="20" pattern="[a-zA-Z][a-zA-Z0-9._-]{1,19}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col">
                        <label>
                            <fmt:message key="profile.email"/>
                        </label>
                        <input class="form-control" type="text" placeholder=
                                "<fmt:message key="profile.placeholder.email"/>"
                                value="${user.email}" required name="email" id="email"
                               pattern="[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,100})">
                        <div class="form-row" style="margin-top: 20px;">
                            <div class="col">
                                <input type="checkbox" name="show" id="show" value="true"
                                <c:if test="${user.showEmail eq true}">
                                       checked
                                </c:if>>
                                <label style="margin-left: 20px;">
                                    <fmt:message key="profile.contact"/>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col">
                        <label>
                            <fmt:message key="profile.specialization"/>
                        </label>
                        <select class="form-control" required name="choose" id="choose">
                            <optgroup label="<fmt:message key="specialization.chooseSpecialization"/>">
                                <option value="it"
                                <c:if test="${user.specialization=='IT'}">
                                    selected=""
                                </c:if>>
                                    <fmt:message key="specialization.it"/>
                                </option>
                                <option value="administration"
                                        <c:if test="${user.specialization=='ADMINISTRATION'}">
                                    selected=""
                                </c:if>>
                                    <fmt:message key="specialization.administration"/>
                                </option>
                                <option value="finances"
                                        <c:if test="${user.specialization=='FINANCES'}">
                                    selected=""
                                </c:if>>
                                    <fmt:message key="specialization.finances"/>
                                </option>
                                <option value="art"
                                        <c:if test="${user.specialization=='ART'}">
                                            selected=""
                                        </c:if>>
                                    <fmt:message key="specialization.art"/>
                                </option>
                                <option value="marketing"
                                        <c:if test="${user.specialization=='MARKETING'}">
                                            selected=""
                                        </c:if>>
                                    <fmt:message key="specialization.marketing"/>
                                </option>
                                <option value="medicine"
                                        <c:if test="${user.specialization=='MEDICINE'}">
                                            selected=""
                                        </c:if>>
                                    <fmt:message key="specialization.medicine"/>
                                </option>
                                <option value="science"
                                        <c:if test="${user.specialization=='SCIENCE'}">
                                            selected=""
                                        </c:if>>
                                    <fmt:message key="specialization.science"/>
                                </option>
                                <option value="security"
                                        <c:if test="${user.specialization=='SECURITY'}">
                                            selected=""
                                        </c:if>>
                                    <fmt:message key="specialization.security"/>
                                </option>
                                <option value="engineering"
                                        <c:if test="${user.specialization=='ENGINEERING'}">
                                            selected=""
                                        </c:if>>
                                    <fmt:message key="specialization.engineering"/>
                                </option>
                                <option value="vehicles"
                                        <c:if test="${user.specialization=='VEHICLES'}">
                                            selected=""
                                        </c:if>>
                                    <fmt:message key="specialization.vehicles"/>
                                </option>
                                <option value="other"
                                        <c:if test="${user.specialization=='OTHER'}">
                                            selected=""
                                        </c:if>>
                                    <fmt:message key="specialization.other"/>
                                </option>
                            </optgroup>
                    </select></div>
                </div>
                <div class="form-group">
                    <div class="col">
                        <label>
                            <fmt:message key="profile.about"/>
                        </label>

                        <textarea class="form-control" placeholder=
                                "<fmt:message key="profile.placeholder.description"/>" minlength="20" maxlength="255"
                          required name="description" id="description" pattern="[\p{L}0-9!?%#&*+,.():\s-_]"
                        ><c:if test="${user.description eq null}"><fmt:message
                                key="profile.description.empty"/></c:if><c:if
                                test="${user.description != null}">${user.description}</c:if></textarea>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col text-center">
                        <button class="btn btn-primary text-center" type="submit">
                            <fmt:message key="profile.button.save"/>
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