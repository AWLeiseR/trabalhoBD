<%-- 
    Document   : update
    Created on : 14/09/2020, 08:55:12
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:autenticationAutor context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edidção de Postagem</title>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center">Inserção de uma nova postagem</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/posts/update"
                <%--enctype="multipart/form-data"--%>
                method="POST">
                
                <input type="hidden" name="postagemId" value="${post.postagemId}">
                
                <div class="form-group">
                    <label for="post-titulo" class="control-label">Título</label>
                    <input id="post-titulo" class="form-control" type="text" name="titulo" value="${post.titulo}" required autofocus/>
                </div>
                
                <div class="form-group">
                    <label for="post-subtitulo" class="control-label">Subtitulo</label>
                    <input id="post-subtitulo" class="form-control" type="text" name="subtitulo" value="${post.subtitulo}" required/>
                </div>
                
                <div class="form-group">
                    <label class="control-label" for="post-descricao">Descricao</label>
                    <input id="post-descricao" class="form-control" type="text" name="descricao" value="${post.descricao}" required />
                </div>
                
                <div class="form-group">
                    <label class="control-label" for="post-conteudo">Conteudo</label>
                    <textarea id="post-conteudo" class="form-control" type="text" name="conteudo" required >${post.conteudo}</textarea>
                </div>
                <select name="area" id="area" class="form-control form-group">
                <c:forEach var="area" items="${requestScope.areasList}">
                    <c:choose>

                        <c:when test="${requestScope.areaDeBusca == area.areaId}">
                            <option value="${area.areaId}" selected><c:out value="${area.nome}"/></option>
                        </c:when>
                       <c:otherwise >
                           <option value="${area.areaId}" ><c:out value="${area.nome}"/></option>
                        </c:otherwise>

                    </c:choose>
                </c:forEach> 
                </select>
                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
                </div>
            </form>
        </div>

        <%@include file="/view/includes/scripts.jsp" %>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>
