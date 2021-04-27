<%-- 
    Document   : user
    Created on : 25/04/2021, 17:43:38
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dash Usuário</title>
    </head>
    <body>
        <div class="container">
            <div class="card">
                <div class="card-header">
                    <div class="h4">Média de cadastro de usuários mensais:</div>
                </div>
                <div class="card-body">
                    <p> <c:out value="${requestScope.avgUser}"/></p>
                </div>
            </div>
            <div class="container">
                <canvas id="myChart"></canvas>
            </div>
                <div class="h4">Usuários mais engajados</div>
            <table class="table">
                <thead>
                <th class="h4">Score</th>
                <th class="h4">Id do usuário</th>
            </thead>
                <tbody>
                    <c:forEach var="user" items="${requestScope.scoreUser}">
                        <tr>
                            <td>
                                <p><c:out value="${user.intField}"/></p>
                            </td>
                             <td>
                                <p><c:out value="${user.intFieldTwo}"/></p>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script>
            let v = [<%= request.getAttribute("repoQtd")%>];
            let v2 = [<%= request.getAttribute("repoDate")%>];
            let v3 = <%= request.getAttribute("label")%>;
            
            let myChart = document.getElementById('myChart').getContext('2d');
            let graph = new Chart(myChart,{
                type:'bar',
                data:{
                    labels:v2,
                    datasets:[{
                            label:'usuários',
                            data:v
                    }],
                },
                options:{
                    title:{
                      display:true,
                      text:'inscrição de usuários',
                      fontSize:25
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            }); 
        </script>
        <%@include file="/view/includes/scripts.jsp" %>
    </body>
</html>
