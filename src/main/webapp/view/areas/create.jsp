<%-- 
    Document   : create
    Created on : 14/09/2020, 08:55:28
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:autenticationModerador context="${pageContext.servletContext.contextPath}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Criação de Área</title>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center">Inserção de uma nova área</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/areas/create"
                <%--enctype="multipart/form-data"--%>
                method="POST">
                
                <div class="form-group">
                    <label for="area-nome" class="control-label">Nome</label>
                    <input id="usuario-nome" class="form-control" type="text" name="nome" required autofocus/>
                </div>
                
                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
                </div>
            </form>
    </body>
</html>
