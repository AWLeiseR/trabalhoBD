<%-- 
    Document   : updateFunction
    Created on : 14/12/2020, 16:52:39
    Author     : Alan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Função atual: <c:out value="${funcao}"/></h2>
        <form
            class="form"
                action="${pageContext.servletContext.contextPath}/user/updatefunction"
                method="POST" accept-charset="utf-8">
            <input type="hidden" name="userId" value="${id}">
            <select name="funcao" id="funcao" class="form-control form-group">
                <option value="membro" >membro</option>
                <option value="autor" >autor</option>
                <option value="moderador" >moderador</option>
            </select>
            <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar alteração</button>
                </div>
        </form>
    </body>
</html>
