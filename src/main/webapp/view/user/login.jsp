<%-- 
    Document   : login
    Created on : 06/12/2020, 16:43:34
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
         <%@include file="/view/includes/head.jsp"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        
         <div class="jumbotron jumbotron-fluid">
            <div class="container">
             <h1 class="display-4">Comp-magazine!</h1>
             <p class="lead">Conteúdo de computação feito por computeiros.</p>
            </div>
            
        </div>
        
        <div class="container">
            <div class="row justify-content-center">
                <form class="col-5">
                    <div class="form-group">
                        <label for="inputEmail">Email</label>
                        <input type="email" class="form-control" id="inputEmail" aria-describedby="emailHelp">
                        
                    </div>
                    <div class="form-group">
                        <label for="inputSenha">Senha</label>
                        <input type="password" class="form-control" id="inputSenha">
                    </div>
                   <div class="d-flex justify-content-md-center">
                        <button type="submit" class="btn btn-primary btn-lg">Entrar</button>
                   </div>
                    
              </form> 
            </div>
        </div>
    </body>
</html>
