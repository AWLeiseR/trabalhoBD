<%-- 
    Document   : main
    Created on : 15/04/2021, 11:13:00
    Author     : Alan
--%>

<%@page import="model.AuxReport"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
    </head>
    
        <script>
           
        </script>
        <div class="container">
            <canvas id="myChart"></canvas>
        </div>    
                
        <script>
            let v = [<%= request.getAttribute("repoQtd")%>];
            let v2 = [<%= request.getAttribute("repoDate")%>];
            let myChart = document.getElementById('myChart').getContext('2d');
            let graph = new Chart(myChart,{
                type:'line',
                data:{
                    labels:v2,
                    datasets:[{
                            label:'Usuários',
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
        
    
</html>
