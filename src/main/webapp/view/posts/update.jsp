<%-- 
    Document   : update
    Created on : 14/09/2020, 08:55:12
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
                </div>
            </form>
        </div>

        <%@include file="/view/includes/scripts.jsp" %>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>
