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
                          <a class="btn btn-light mb-2" href="${pageContext.servletContext.contextPath}/logout">Logout</a>
                         <a href="${pageContext.servletContext.contextPath}/user/read?id=${usuario.userId}" class="btn btn-light mb-2">Meu perfil</a>
                         <c:if test="${usuario.funcao == 'autor' || usuario.funcao == 'moderador' }">
                          <a href="${pageContext.servletContext.contextPath}/posts/create"" class="btn btn-light mb-2">Escrever Postagem</a>
                         </c:if>
                         <c:if test="${usuario.funcao == 'moderador' }">
                             <a href="${pageContext.servletContext.contextPath}/areas/create" class="btn btn-light mb-2">Criar área</a>
                             <a href="${pageContext.servletContext.contextPath}/areas" class="btn btn-light mb-2">Gerenciar áreas</a>
                             
                           <a href="${pageContext.servletContext.contextPath}/posts" class="btn btn-light mb-2">Gerenciar postagens</a>
                            <a href="${pageContext.servletContext.contextPath}/user" class="btn btn-light mb-2">Gerenciar usuários</a>  
                            <a href="${pageContext.servletContext.contextPath}/dashboard" class="btn btn-light mb-2">Dashboard</a> 
                         </c:if>
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
                        <form class="form"
                            action="${pageContext.servletContext.contextPath}/busca"
                            method="POST">
                            <label>Ordenar Postagens por: </label>
                            <div class="form-group">
                                <c:choose>
                                    <c:when test="${ordenarPor == 1}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="order" id="Mais_vistos" value="1" checked>
                                            <label class="form-check-label" for="order">
                                              Mais vistos
                                            </label>
                                          </div>
                                          <div class="form-check">
                                            <input class="form-check-input" type="radio" name="order" id="Mais_recentes" value="2">
                                            <label class="form-check-label" for="order">
                                              Mais recentes
                                            </label>
                                          </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="order" id="Mais_vistos" value="1" >
                                            <label class="form-check-label" for="order">
                                              Mais vistos
                                            </label>
                                          </div>
                                          <div class="form-check">
                                            <input class="form-check-input" type="radio" name="order" id="Mais_recentes" value="2" checked>
                                            <label class="form-check-label" for="order">
                                              Mais recentes
                                            </label>
                                          </div>
                                    </c:otherwise>
                                </c:choose>
                                
                            </div>
                                
                                
                            
                            <label for="area">Escolher area das Postagens: </label>
                            <select name="area" id="area" class="form-control form-group">
                           
                               <c:forEach var="area" items="${requestScope.areasList}">
                                    <c:choose>
                                        
                                        <c:when test="${areaDeBusca == area.areaId}">
                                            <option value="${area.areaId}" selected><c:out value="${area.nome}"/></option>
                                        </c:when>
                                       <c:otherwise >
                                           <option value="${area.areaId}" ><c:out value="${area.nome}"/></option>
                                        </c:otherwise>
                                                                            
                                    </c:choose>
                                </c:forEach> 
                             </select>
                            <br/>
                           <button type="submit" class="btn btn-light mb-2">aplicar</button>
                            
                        </form>
                        
                                                   
                    </div>
                        
                </div>
            </div>
        </div>
            
    </body>
</html>
