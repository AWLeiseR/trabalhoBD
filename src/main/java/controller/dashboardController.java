/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DAO;
import dao.DAOFactory;
import dao.PostagemDAO;
import dao.UserDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import model.AuxReport;

/**
 *
 * @author Alan
 */
@WebServlet(name = "dashboardController", urlPatterns = {"/dashboard",})
public class dashboardController extends HttpServlet {

//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
//        
//        HttpSession session;
//        RequestDispatcher dispatcher = null;
//        
//        PostagemDAO post;
//        UserDAO user;
//        List<AuxReport> repo;
//        response.setContentType("text/html;charset=UTF-8");
//         try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
//                post = (PostagemDAO) daoFactory.getPostagemDAO();
//                user = (UserDAO) daoFactory.getUserDAO();
//                repo = user.getUserPerDay();
//                
//                try (PrintWriter out = response.getWriter()) {
//                    /* TODO output your page here. You may use following sample code. */
//                    out.println("<!DOCTYPE html>");
//                    out.println("<html>");
//                    out.println("<head>");
//                    out.println("<title>Servlet dashboardController</title>");            
//                    out.println("</head>");
//                    out.println("<body>");
//                    out.println("<h1>Servlet dashboardController at " + request.getContextPath() + "</h1>");
//                    for(int i = 0 ; i<repo.size();i++){
//                        out.print("<p>" + repo.get(i).getIntField() + "-" + repo.get(i).getDateField()+"</p>");
//                    }
//                    out.println("</body>");
//                    out.println("</html>");
//                }
//                         
//         }
//        
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
            RequestDispatcher dispatcher = null;
            PostagemDAO post;
            UserDAO user;
            List<AuxReport> repo;
            
             HttpSession session;
           switch(request.getServletPath()){
               case "/dashboard":
                    try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        post = (PostagemDAO) daoFactory.getPostagemDAO();
                        user = (UserDAO) daoFactory.getUserDAO();
                        repo = user.getUserPerDay();
                        String[] vet = new String[repo.size()];
                        String[] auxString = new String[repo.size()];
                        for(int i = 0;i<repo.size();i++){
                            
                             vet[i] = Integer.toString( repo.get(i).getIntField());
                            auxString[i] ="'" +  repo.get(i).getDateField().toString()+"'";
                        }
                        String messagesCSV = String.join(",", vet);
                        String messagesCSV2 = String.join(",", auxString);
                        request.setAttribute("repoQtd", messagesCSV);
                        request.setAttribute("repoDate", messagesCSV2);
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dispatcher = request.getRequestDispatcher("/view/dashboard/main.jsp");
                    dispatcher.forward(request, response);
                    break;


                   
               default:
                   System.out.println(request.getServletPath());
                    dispatcher = request.getRequestDispatcher("/view/dashboard/main.jsp");
                    dispatcher.forward(request, response);
                    break;
           }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
