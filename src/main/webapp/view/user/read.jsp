<%-- 
    Document   : read
    Created on : 14/09/2020, 15:34:11
    Author     : Alan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfil</title>
    </head>
    <body>
        
        <div class="container">
            <div class="card">
                <div class="card-header">
                    <span class="h4">Id: <c:out value="${user.userId}"/></span>
                </div>
                 <div class="card-body">
                    <div class="row">
                        <div class="col">
                              
                         <span class="h4">Nome: <c:out value="${user.nome}"/></span>

                        </div>
                        <div class="col">
                              
                          <span class="h4">Sobrenome: <c:out value="${user.sobrenome}"/></span>

                        </div>

                      </div>
                        <div class="row">
                            <div class="col">
                              
                              <span class="h4">Email: <c:out value="${user.email}"/></span>
                            </div>
                            <div class="col">
                              
                              <span class="h4">Função: <c:out value="${user.funcao}"/></span> 
                            </div>

                        </div>
                </div>
            </div>
              
            <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/user/update?id=${user.userId}">
                <span>Alterar informações</span>
            </a>
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/">
                <span>Voltar para o início</span>
            </a>
        </div>
       
    </body>
</html>
