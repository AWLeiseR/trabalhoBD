<%-- 
    Document   : index
    Created on : 14/09/2020, 08:42:40
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:autenticationModerador context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Postagens</title>
    </head>
    <body>
        <div class="container">
            
            <form>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="col-lg-2 h4">ID</th>
                            <th class="col-lg-5 h4">Titulo</th>
                            <th class="col-lg-4 h4 text-center">Ação</th>
                            <th class="col-lg-1 h4 text-center">Excluir?</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="post" items="${requestScope.postList}">
                            <tr>
                                <td>
                                    <span class="h4"><c:out value="${post.postagemId}"/></span>
                                </td>
                                <td>
                                    <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                                        <span class="h4"><c:out value="${post.titulo}"/></span>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <a class="btn btn-default"
                                       href="${pageContext.servletContext.contextPath}/posts/update?id=${post.postagemId}"
                                       data-toggle="tooltip"
                                       data-original-title="Editar">
                                        <i class="fa fa-pencil"></i>
                                    </a>
                                    <a class="btn btn-default link_excluir_area"
                                       href="${pageContext.servletContext.contextPath}/posts/delete?id=${post.postagemId}"
                                       data-toggle="tooltip"
                                       data-original-title="Excluir">
                                        <i class="fa fa-trash"></i>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <input class="checkbox-inline" type="checkbox" name="delete" value="${post.postagemId}" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
      
        </div>  
                
        <%@include file="/view/includes/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>
