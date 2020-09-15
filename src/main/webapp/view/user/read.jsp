<%-- 
    Document   : read
    Created on : 14/09/2020, 15:34:11
    Author     : Alan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View profile</title>
    </head>
    <body>
        <div>
            <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="col-lg-2">ID</th>
                            <th class="col-lg-5 ">Nome</th>
                            <th class="col-lg-4 ">Sobrenome</th>
                            <th class="col-lg-1 ">Email</th>
                            <th class="col-lg-1 ">Função</th>
                        </tr>
                    </thead>
                    <tbody>
                    
                        <tr>
                            <td>
                                <span class="h4"><c:out value="${user.userId}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${user.nome}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${user.sobrenome}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${user.email}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${user.funcao}"/></span>
                            </td>
                        </tr>
                   
                    </tbody>
            </table>
            <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/user">
                <span>Voltar a pagina principal</span>
            </a>
        </div>
    </body>
</html>
