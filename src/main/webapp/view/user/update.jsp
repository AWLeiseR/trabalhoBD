<%-- 
    Document   : create
    Created on : Sep. 10, 2020, 16:27:40 a.m.
    Author     : Alan
--%>

<%@page import="java.util.Enumeration"%>
<%@page import="java.util.List"%>
<%@page import="model.AreasDeInteresse"%>
<%@page import="model.UserAreas"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <title>Alterar perfil</title>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Modificação de Perfil do usuário</h2>

            
            <form class="form"
                action="${pageContext.servletContext.contextPath}/user/update"
                <%--enctype="multipart/form-data"--%>
                method="POST">
                <input type="hidden" name="userId" value="${user.userId}">
                
                <div class="form-group">
                    <label for="usuario-nome" class="control-label">Nome</label>
                    <input id="usuario-nome" class="form-control" type="text" name="nome" value="${user.nome}" required autofocus/>
                </div>
                
                <div class="form-group">
                    <label for="usuario-sobrenome" class="control-label">Sobrenome</label>
                    <input id="usuario-sobrenome" class="form-control" type="text" name="sobrenome" value="${user.sobrenome}" required/>
                </div>
                
                <div class="form-group">
                    <label class="control-label" for="usuario-email">Email</label>
                    <input id="usuario-email" class="form-control" type="text" name="email" value="${user.email}" required />

                    <p class="help-block"></p>
                </div>


                <div class="form-group">
                    <label class="control-label">Senha</label>
                    <input class="form-control password-input"
                           type="password" name="senha"
                           pattern=".{4,}"  title="Pelo menos 4 caracteres."/>
                </div>

                <div class="form-group pwd-confirm">
                    <label class="control-label">Confirmar senha</label>
                    <input class="form-control password-confirm"
                           type="password" name="senha-confirmacao"
                           pattern=".{4,}" title="Pelo menos 4 caracteres."/>
                    <p class="help-block"></p>
                </div>
                    
                
                    <label >Escolha uma Área de interesse</label><br>
                    <%
                        HttpSession s = request.getSession();
                        //List<AreasDeInteresse> b =(List<AreasDeInteresse>) requestScope.areasList;
                        int i,j,x=0;
                        
                        List<UserAreas> a = (List<UserAreas>) s.getAttribute("userAreasList" );
                       List<AreasDeInteresse> b =(List<AreasDeInteresse>) session.getAttribute("areasList");
                       
                        for(i=0;i<b.size();i++){
                            
                            for(j=0;j<a.size();j++){
                               if(b.get(i).getAreaId() == a.get(j).getIdAreas()){
                                    x=1;
                                    break;
                                }
                            }
                            if(x==1){
                                out.print("<input type=\"checkbox\" id=\""+ b.get(i).getAreaId() +"\" name=\"areas\" value=\""+ b.get(i).getAreaId() +"\" checked>");
                            }else{
                                out.print("<input type=\"checkbox\" id=\""+ b.get(i).getAreaId() +"\" name=\"areas\" value=\""+ b.get(i).getAreaId() +"\">");
                            }
                            
                            out.print("<label for=\""+ b.get(i).getAreaId() +"\">" + b.get(i).getNome() + "</label><br>");
                            x=0;
                        }
                    %>
                    
                    <!-- <c:forEach var="area" items="${requestScope.areasList}"> 
                        <c:forEach var="userAreas" items="${requestScope.userAreasList}">
                            <c:choose>
                                <c:when test="${userAreas.idAreas == area.areaId}">
                                   <input type="checkbox" id="${area.areaId}" name="areas" value="${area.areaId}" checked>
                                   <label for="${area.areaId}"><c:out value="${area.nome}"/></label><br>
                               </c:when>
                               <c:otherwise >
                                     <input type="checkbox" id="${area.areaId}" name="areas" value="${area.areaId}" >
                                     <label for="${area.areaId}"><c:out value="${area.nome}"/></label><br>
                               </c:otherwise>
                            </c:choose>
                        </c:forEach>
                       
                    </c:forEach> -->
                
                    
                    <input type="hidden" name="funcao" value="${user.funcao}">
                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
                </div>
            </form>
        </div>

        <%@include file="/view/includes/scripts.jsp" %>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>