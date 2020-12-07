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
        <title>Cadastro de membros</title>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Cadastro de novo membroo</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/user/create"
                <%--enctype="multipart/form-data"--%>
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
                
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label>Senha</label>
                        <input class="form-control password-input" type="password" name="senha" pattern=".{4,}" required title="Pelo menos 4 caracteres."/>
                    </div>
                     <div class="form-group col-md-6">
                        <label >Confirmar senha</label>
                        <input class="form-control password-confirm" type="password" name="senha-confirmacao" pattern=".{4,}" required title="Pelo menos 4 caracteres."/>
                        <p class="help-block"></p>
                    </div>

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