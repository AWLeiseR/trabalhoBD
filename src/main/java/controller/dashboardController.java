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
import dao.VisualizacoesDAO;
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
@WebServlet(
        name = "dashboardController", 
        urlPatterns = {
            "/dashboard",
            "/dashboardpost",
            "/dashboarduser",
            "/dashboardview",
        })
public class dashboardController extends HttpServlet {
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
            VisualizacoesDAO view;
            List<AuxReport> repo = null;
            String label = new String();
            HttpSession session = request.getSession(false);
            String[] vet;
            String[] auxString;
            String messages ;
            String messages2 ;
            
         try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
            post = (PostagemDAO) daoFactory.getPostagemDAO();
            user = (UserDAO) daoFactory.getUserDAO();
            view = (VisualizacoesDAO) daoFactory.getVisualizacoesDAO();
            switch(request.getServletPath()){
               case "/dashboard":
                   
                    int totalView = view.getTotalView();
                    int totalUsers = user.getTotalUser();
                    int totalPost = post.getTotalPost();
                    request.setAttribute("totalPost", totalPost);
                    request.setAttribute("totalUsers", totalUsers);
                    request.setAttribute("totalView", totalView);
                    dispatcher = request.getRequestDispatcher("/view/dashboard/main.jsp");
                    dispatcher.forward(request, response);
                    break;
               case "/dashboardpost":
                    repo = post.getPostPerDay();
                    vet = new String[repo.size()];
                    auxString = new String[repo.size()];
                    for(int i = 0;i<repo.size();i++){

                         vet[i] = Integer.toString( repo.get(i).getIntField());
                        auxString[i] ="'" +  repo.get(i).getDateField().toString()+"'";
                    }
                    messages = String.join(",", vet);
                    messages2 = String.join(",", auxString);
                    request.setAttribute("repoQtd", messages);
                    request.setAttribute("repoDate", messages2); 
                    dispatcher = request.getRequestDispatcher("/view/dashboard/post.jsp");
                    dispatcher.forward(request, response);
                   break;
               case "/dashboarduser":
                    repo = user.getUserPerDay();
                    vet = new String[repo.size()];
                    auxString = new String[repo.size()];
                        for(int i = 0;i<repo.size();i++){

                             vet[i] = Integer.toString( repo.get(i).getIntField());
                            auxString[i] ="'" +  repo.get(i).getDateField().toString()+"'";
                        }
                        messages = String.join(",", vet);
                        messages2 = String.join(",", auxString);
                        request.setAttribute("repoQtd", messages);
                        request.setAttribute("repoDate", messages2); 
                    dispatcher = request.getRequestDispatcher("/view/dashboard/user.jsp");
                    dispatcher.forward(request, response);
                   break;
               case "/dashboardview":
                    repo = view.getViewPerDay();
                    vet = new String[repo.size()];
                    auxString = new String[repo.size()];
                    for(int i = 0;i<repo.size();i++){

                         vet[i] = Integer.toString( repo.get(i).getIntField());
                        auxString[i] ="'" +  repo.get(i).getDateField().toString()+"'";
                    }
                    messages = String.join(",", vet);
                    messages2 = String.join(",", auxString);
                    request.setAttribute("repoQtd", messages);
                    request.setAttribute("repoDate", messages2); 
                    dispatcher = request.getRequestDispatcher("/view/dashboard/view.jsp");
                    dispatcher.forward(request, response);
                   break;                   
               default:
                   
                    dispatcher = request.getRequestDispatcher("/view/dashboard/main.jsp");
                    dispatcher.forward(request, response);
                    break;
           }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
           
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
