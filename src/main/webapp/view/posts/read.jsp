<%-- 
    Document   : read
    Created on : 15/09/2020, 01:25:33
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="Windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View postagem</title>
    </head>
    <body>
        <div class="container">
            <div class="card">
                <div class="card-header">
                    <span class="h4"><c:out value="${post.titulo}"/></span>
                </div>
                <div class="card-body">
                    <p>
                    <span class="h4"><c:out value="${post.subtitulo}"/></span>
                    </p>
                    
                    <p> 
                        <span class="card-text"><c:out value="${post.conteudo}"/></span>
                    </p>
                   
                </div>
                     <div class="card-footer">
                         <c:choose>
                            <c:when test="${aux == 0}">
                                <a href="${pageContext.servletContext.contextPath}/posts/highlight?id=${usuario.userId}&idPostagem=${post.postagemId}" >
                                    <div class="btn btn-outline-primary">
                                        <i class="fa fa-angle-double-up"></i>
                                        <p>Dar um Highlight</p>
                                    </div>
                                </a>
                            </c:when>     

                            <c:when test="${aux == 1}">
                                <a href="${pageContext.servletContext.contextPath}/posts/tirarHighlight?id=${usuario.userId}&idPostagem=${post.postagemId}" >
                                    <div class="btn btn-outline-primary">
                                        <i class="fa fa-angle-double-up"></i>
                                        <p>Tirar o Highlight</p>
                                    </div>
                                </a>
                            </c:when>
                              
                            <c:otherwise >
                                
                            </c:otherwise >
                        </c:choose>
                        
                         
                        <span class="card-text">Id: <c:out value="${post.postagemId}"/></span>
                        <span class="card-text">Visualiza��es: <c:out value="${post.visualizacoes}"/></span>
                        <span class="card-text">CreateAt: <c:out value="${post.createAt}"/></span>
                        <span class="card-text">UpdateAt: <c:out value="${post.alteradoAt}"/></span>
                     </div>
            </div>
            <div>
                <span class="h4">Veja essas postagens tamb�m:</span>
                <c:forEach var="post" items="${requestScope.relatedPost}">
                    <div class="card">
                        
                        <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                            <div class="card-header">
                               <span class="h4"><c:out value="${post.titulo}"/></span> 
                            </div>
                            <div class=" card-text">
                                <span class="h4"><c:out value="${post.subtitulo}"/></span>
                            </div>
                            
                    </a>
                    </div></br>
                </c:forEach>
            </div>  
            <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}">
                <span>Voltar a pagina principal</span>
            </a>
        </div>
    </body>
</html>
