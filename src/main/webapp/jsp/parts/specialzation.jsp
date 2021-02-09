<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${sessionScope.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle scope="session" basename="properties.language"/>


<html>
<head>
</head>
<body>
<optgroup label=
                  "<fmt:message key="specialization.chooseSpecialization"/>">
    <option value="it">
        <fmt:message key="specialization.it"/>
    </option>
    <option value="administration">
        <fmt:message key="specialization.administration"/>
    </option>
    <option value="finances">
        <fmt:message key="specialization.finances"/>
    </option>
    <option value="art">
        <fmt:message key="specialization.art"/>
    </option>
    <option value="marketing">
        <fmt:message key="specialization.marketing"/>
    </option>
    <option value="medicine">
        <fmt:message key="specialization.medicine"/>
    </option>
    <option value="science">
        <fmt:message key="specialization.science"/>
    </option>
    <option value="security">
        <fmt:message key="specialization.security"/>
    </option>
    <option value="engineering">
        <fmt:message key="specialization.engineering"/>
    </option>
    <option value="vehicles">
        <fmt:message key="specialization.vehicles"/>
    </option>
    <option value="other" selected="">
        <fmt:message key="specialization.other"/>
    </option>
</optgroup>
</body>
</html>
