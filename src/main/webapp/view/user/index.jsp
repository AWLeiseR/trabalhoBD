<%-- 
    Document   : index
    Created on : Sep. 1, 2020, 10:40:35 a.m.
    Author     : dskaster
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp"%>
        <title>[BD 2020] Usuários</title>
    </head>
    <body>

        <div class="container">
            
            <form class="form_excluir_usuarios" action="${pageContext.servletContext.contextPath}/user/delete" method="POST">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="col-lg-2 h4">ID</th>
                            <th class="col-lg-5 h4">Nome</th>
                            <th class="col-lg-4 h4 text-center">Ação</th>
                            <th class="col-lg-1 h4 text-center">Excluir?</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usuario" items="${requestScope.userList}">
                            <tr>
                                <td>
                                    <span class="h4"><c:out value="${usuario.userId}"/></span>
                                </td>
                                <td>
                                        <span class="h4"><c:out value="${usuario.nome}"/></span>
                                </td>
                                <td class="text-center">
                                    <a class="btn btn-default"
                                       href="${pageContext.servletContext.contextPath}/user/update?id=${usuario.userId}"
                                       data-toggle="tooltip"
                                       data-original-title="Editar">
                                        <i class="fa fa-pencil"></i>
                                    </a>
                                    <a class="btn btn-default link_excluir_usuario"
                                       href="#"
                                       data-href="${pageContext.servletContext.contextPath}/user/delete?id=${usuario.userId}"
                                       data-toggle="tooltip"
                                       data-original-title="Excluir">
                                        <i class="fa fa-trash"></i>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <input class="checkbox-inline" type="checkbox" name="delete" value="${usuario.userId}" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
                <div class="text-center div_inserir_excluir">
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/user/create">
                    Inserir novo usuário
                </a>

                <button class="btn btn-lg btn-warning" data-toggle="modal" data-target=".modal_excluir_usuarios">
                    Excluir múltiplos usuários
                </button>
                <a class="btn btn-default"
                   href="${pageContext.servletContext.contextPath}/logout"
                   data-toggle="tooltip"
                   data-original-title="Logout">
                    <i class="fa fa-sign-out"></i>
                </a>
            </div>
        </div>  
                
        <%@include file="/view/includes/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>