<%-- 
    Document   : view
    Created on : 25/04/2021, 17:43:27
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
        <title>Dash Visualizacoes</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <div class="card-header">
                            <div class="h4">Média de visualizações por dia (30 dias)</div>
                        </div>
                        <div class="card-body">
                             <p><c:out value="${requestScope.avgView}"/></p>
                        </div>
                    </div> 
                </div>
                <div class="col">
                    <div class="card">
                      <div class="card-header">
                          <div class="h4">Porcentagem de variação diária</div>
                      </div>
                      <div class="card-body">
                          <p> <c:out value="${requestScope.balanco}"/> %</p>
                      </div>  
                  </div>  
                </div>
            </div>
           
            
            <div class="container">
                <canvas id="myChart"></canvas>
            </div>
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
                            label:'visualizações',
                            data:v
                    }],
                },
                options:{
                    title:{
                      display:true,
                      text:'Visualizações por dia',
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
