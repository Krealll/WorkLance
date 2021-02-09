<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Adding members</title>
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css?h=8fbddb265b84cd88828a6b52211b1304">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/assets/css/smoothproducts.css?h=38a515b6b30123300b3619cce6411cec">
    <link rel="icon" href="/img/fav.png" type="image/icon">
</head>

<body  onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<c:import url="parts/header.jsp"/>
<main class="page faq-page">
    <section class="clean-block clean-faq dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info" style="margin-top: 20px">
                    <fmt:message key="team.creation.title"/>
                </h2>
            </div>
            <form method="post" action="projectServlet">
                <input type="hidden" name="command" value="add_members"/>
                <input type="hidden" name="number_of_members" value="${number_of_members}">
                <div class="form-group">
                    <div class="col">
                        <div class="form-row">
                            <div class="col">
                                <label class="col-form-label">
                                    <fmt:message key="members.add"/>
                                </label>
                            </div>
                        </div>
                        <c:forEach var="i" begin="1" end="${number_of_members}" >
                            <div class="form-row">
                                <div class="col">
                                    <input class="form-control" required name="member_${i}" id="member_${i}"
                                           type="text" placeholder=
                                            "<fmt:message key="members.placeholder"/>"
                                           style="margin-top: 20px;" minlength="1" maxlength="20"
                                           pattern="[a-zA-Z][a-zA-Z0-9._-]{1,19}">
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col text-center">
                        <button class="btn btn-primary text-center" type="submit">
                            <fmt:message key="members.button"/>
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
<script src="/assets/js/theme.js?h=417b03f5f0a4f9f27f8c248f532eb5af">

</script>
</body>

</html>