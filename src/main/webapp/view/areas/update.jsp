<%-- 
    Document   : update
    Created on : 14/09/2020, 08:56:04
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update area</title>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center">Modificação Areas</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/areas/update"
                <%--enctype="multipart/form-data"--%>
                method="POST">
                
                <input type="hidden" name="areaId" value="${area.areaId}">
                
                <div class="form-group">
                    <label for="area-nome" class="control-label">Nome</label>
                    <input id="area-nome" class="form-control" type="text" name="nome" value="${area.nome}" required autofocus/>
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
