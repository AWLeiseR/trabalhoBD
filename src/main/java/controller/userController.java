/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAO;
import dao.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import java.util.Random;

/**
 *
 * @author Alan
 */
@WebServlet(
        name = "UserController",
        urlPatterns = {
            "/user",
            "/user/create"})
public class userController extends HttpServlet {
    
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO<User> dao;
        User user;
        RequestDispatcher dispatcher;
        switch (request.getServletPath()) {
            case "/user": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getDAO();

                    List<User> userList = dao.all();
                    request.setAttribute("userList", userList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/user/index.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/user/create":{
                dispatcher = request.getRequestDispatcher("/view/user/create.jsp");
                dispatcher.forward(request, response);
                break;
            }

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
            DAOFactory daoFactory;
        try {
            daoFactory = DAOFactory.getInstance();
            DAO<User> dao=daoFactory.getDAO();
            User user=new User();
            RequestDispatcher dispatcher;
            switch (request.getServletPath()) {
                
                case "/user/create":{
                    
                    try {
                        Random generate= new Random();
                        int id=generate.nextInt(1000);
                        user.setUserId(id);
                        String nome= request.getParameter("nome");
                        user.setNome(nome);
//                        user.setSobrenome(request.getParameter("sobrenome"));
                        String sobrenome= request.getParameter("sobrenome");
                        user.setSobrenome(sobrenome);
//                        user.setEmail(request.getParameter("email"));
                        String email= request.getParameter("email");
                        user.setEmail(email);
                        String senha= request.getParameter("senha");
                        user.setSenha(senha);
//                        user.setSenha(request.getParameter("senha"));
                        String funcao= request.getParameter("funcao");
                        user.setFuncao(funcao);
//                        user.setFuncao(request.getParameter("funcao"));
                        
                        dao.create(user);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
             response.sendRedirect(request.getContextPath() + "/user");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
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
