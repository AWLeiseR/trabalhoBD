<%-- 
    Document   : create
    Created on : 14/09/2020, 08:55:00
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="Windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:autenticationAutor context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cria��o Postagem</title>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center">Inser��o de uma nova postagem</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/posts/create"
                <%--enctype="multipart/form-data"--%>
                method="POST" accept-charset="utf-8">
                
                <div class="form-group">
                    <label for="postagem-titulo" class="control-label">T�tulo</label>
                    <input id="postagem-titulo" class="form-control" type="text" name="titulo" required autofocus/>
                </div>
                
                <div class="form-group">
                    <label for="postagem-subtitulo" class="control-label">Subtitulo</label>
                    <input id="postagem-subtitulo" class="form-control" type="text" name="subtitulo" required/>
                </div>
                
                <div class="form-group">
                    <label for="postagem-descricao" class="control-label">Descricao</label>
                    <input id="postagem-descricao" class="form-control" type="text" name="descricao" required/>
                </div>
                
                <div class="form-group">
                    <label class="control-label" for="postagem-conteudo">Conte�do</label>
                    <textarea id="postagem-conteudo" class="form-control" type="text" name="conteudo" rows="10" required ></textarea>
                </div>
               
                 <label for="area">Escolher area das Postagens:</label>
                <select name="area" id="area" class="form-control form-group">

                   <c:forEach var="area" items="${requestScope.areasList}">

                    <option value="${area.areaId}" ><c:out value="${area.nome}"/></option>

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
