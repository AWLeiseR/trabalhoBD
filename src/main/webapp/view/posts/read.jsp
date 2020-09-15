<%-- 
    Document   : read
    Created on : 15/09/2020, 01:25:33
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View postagem</title>
    </head>
    <body>
        <div class="container">
            <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="col-lg-2">ID</th>
                            <th class="col-lg-5 ">titulo</th>
                            <th class="col-lg-4 ">Subtitulo</th>
                            <th class="col-lg-1 ">Descricao</th>
                            <th class="col-lg-1 ">Conteudo</th>
                            <th class="col-lg-1 ">visualizacoes</th>
                            <th class="col-lg-1 ">CreateAt</th>
                            <th class="col-lg-1 ">alteradoAt</th>
                        </tr>
                    </thead>
                    <tbody>
                    
                        <tr>
                            <td>
                                <span class="h4"><c:out value="${post.postagemId}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${post.titulo}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${post.subtitulo}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${post.descricao}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${post.conteudo}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${post.visualizacoes}"/></span>
                                
                            </td>
                            <td>
                                <span class="h4"><c:out value="${post.createAt}"/></span>
                            </td>
                            <td>
                                <span class="h4"><c:out value="${post.alteradoAt}"/></span>
                            </td>
                        </tr>
                   
                    </tbody>
            </table>
            <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/posts">
                <span>Voltar a pagina principal</span>
            </a>
        </div>
    </body>
</html>
