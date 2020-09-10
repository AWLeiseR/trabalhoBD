<%-- 
    Document   : create
    Created on : Sep. 1, 2020, 11:27:40 a.m.
    Author     : dskaster
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <title>[BD 2020] Usuários: cadastro</title>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Inserção de um novo usuário</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/user/create"
                enctype="multipart/form-data"
                method="POST">
                
                <div class="form-group">
                    <label for="usuario-nome" class="control-label">Nome</label>
                    <input id="usuario-nome" class="form-control" type="text" name="nome" required autofocus/>
                </div>
                
                <div class="form-group">
                    <label for="usuario-sobrenome" class="control-label">Sobrenome</label>
                    <input id="usuario-sobrenome" class="form-control" type="text" name="sobrenome" required/>
                </div>
                
                <div class="form-group">
                    <label class="control-label" for="usuario-email">Email</label>
                    <input id="usuario-email" class="form-control" type="text" name="email" required />

                    <p class="help-block"></p>
                </div>


                <div class="form-group">
                    <label class="control-label">Senha</label>
                    <input class="form-control password-input"
                           type="password" name="senha"
                           pattern=".{4,}" required title="Pelo menos 4 caracteres."/>
                </div>

                <div class="form-group pwd-confirm">
                    <label class="control-label">Confirmar senha</label>
                    <input class="form-control password-confirm"
                           type="password" name="senha-confirmacao"
                           pattern=".{4,}" required title="Pelo menos 4 caracteres."/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Função</label>
                    <input class="form-control" d="usuario-funcao" type="text" name="funcao" required/>
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