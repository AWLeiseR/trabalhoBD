<%-- 
    Document   : homePage
    Created on : 07/12/2020, 16:41:46
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
    <%@include file="/view/includes/head.jsp"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comp-magazine</title>
    </head>
    <body>
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
             <h1 class="display-4">Comp-magazine!</h1>
             <p class="lead">Bem-vindo <c:out value="${usuario.nome}"/>!</p>
            </div>
                <div class="container">
                    <div class="row">
                        
                      <div class="col">
                          
                        <ul class="list-group">
                            <c:forEach var="area" items="${requestScope.areasList}">
                                <c:choose>
                                    <c:when test="${requestScope.areaDeBusca == area.areaId}">
                                        <a href="${pageContext.servletContext.contextPath}/area?area=${area.areaId}">
                                            <li class="list-group-item active"><c:out value="${area.nome}"/></li>
                                        </a>
                                    </c:when>
                                    <c:when test="${requestScope.areaDeBusca != area.areaId}">
                                        <a href="${pageContext.servletContext.contextPath}/area?area=${area.areaId}">
                                            <li class="list-group-item"><c:out value="${area.nome}"/></li>
                                        </a>
                                    </c:when>
                                    <c:otherwise> 
                                    <a href="${pageContext.servletContext.contextPath}/area?area=${area.areaId}">
                                            <li class="list-group-item active"><c:out value="${area.nome}"/></li>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                          
                      </div>
                    <div class="col-6">
                        
                      <c:forEach var="post" items="${requestScope.postList}">
                          <div class="card">
                              <div class="card-header">
                                  <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                                      <span class="card-title h3"><c:out value="${post.titulo}"/></span>
                                  </a>
                              </div>
                              <div class="card-body">  
                                  <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                                      <span class="h4"><c:out value="${post.subtitulo}"/></span>
                                  </a>
                              </div>
                              <div class="card-body">  
                                  <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                                      <span class="h4"><c:out value="${post.descricao}"/></span>
                                  </a>
                              </div>
                          </div>
                                  <br/>
                      </c:forEach>
                                  
                    </div>
                        
                    <div class="col">
                        
                      
                          <c:choose>
                              <c:when test="${requestScope.ordenarPor == 1}">
                                <ul class="list-group">
                                    <a href="${pageContext.servletContext.contextPath}/busca?ordenarPor=1">
                                        <li class="list-group-item active">Novas Postagens</li>
                                    </a>

                                     <a href="${pageContext.servletContext.contextPath}/busca?ordenarPor=2">
                                        <li class="list-group-item">Mais vistas</li>
                                    </a>
                                </ul>
                              </c:when>
                              <c:when test="${requestScope.ordenarPor == 2}">
                                  <ul class="list-group">
                                <a href="${pageContext.servletContext.contextPath}/busca?ordenarPor=1">
                                    <li class="list-group-item ">Novas Postagens</li>
                                </a>

                                 <a href="${pageContext.servletContext.contextPath}/busca?ordenarPor=2">
                                    <li class="list-group-item active">Mais vistas</li>
                                </a>
                                    </ul>
                              </c:when>
                              <c:otherwise>  
                                  <ul class="list-group">
                                    <a href="${pageContext.servletContext.contextPath}/busca?ordenarPor=1">
                                        <li class="list-group-item active">Novas Postagens</li>
                                    </a>

                                     <a href="${pageContext.servletContext.contextPath}/busca?ordenarPor=2">
                                        <li class="list-group-item">Mais vistas</li>
                                    </a>
                                </ul>
                                </c:otherwise>  
                           </c:choose>
                      
                        
                    </div>
                        
                </div>
            </div>
        </div>
            
    </body>
</html>
