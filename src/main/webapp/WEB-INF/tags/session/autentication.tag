<%-- 
    Document   : autentication
    Created on : 14/12/2020, 11:34:04
    Author     : Alan
--%>

<%@tag description="User authentication handler" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="context" required="true"%>

<c:if test="${empty sessionScope.usuario}">
    <c:redirect url="/" />
</c:if>