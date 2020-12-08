/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAO;
import dao.DAOFactory;
import dao.UserDAO;
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
import javax.servlet.http.HttpSession;
import model.AreasDeInteresse;
import model.Postagem;
import model.User;

/**
 *
 * @author Alan
 */
@WebServlet(
        name = "loginController",
        urlPatterns = {
            "",
            "/login",
            "/logout"
        })
public class loginController extends HttpServlet {

   
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
            HttpSession session;
            RequestDispatcher dispatcher = null;
            DAO<AreasDeInteresse> daoAreas;
            DAO<Postagem> daoPostagem;

            switch (request.getServletPath()) {
                case "": {
                    session = request.getSession(false);

                    if (session != null && session.getAttribute("usuario") != null) {
                        try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                            daoAreas = daoFactory.getAreaDAO();
                            daoPostagem = daoFactory.getPostagemDAO();
                            List<Postagem> postList = daoPostagem.all();
                            List<AreasDeInteresse> areasList = daoAreas.all();

                            request.setAttribute("areasList", areasList);
                            dispatcher = request.getRequestDispatcher("/view/homePage.jsp");
                        }   catch (ClassNotFoundException ex) {
                            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        
                        dispatcher = request.getRequestDispatcher("/view/index.jsp");
                    }

                    dispatcher.forward(request, response);

                    break;
                }
                case "/login":{
                    dispatcher = request.getRequestDispatcher("/view/user/login.jsp");
                    dispatcher.forward(request, response);
                    
                    break;
                }
                case "/logout": {
                    session = request.getSession(false);

                    if (session != null) {
                        session.invalidate();
                    }

                    response.sendRedirect(request.getContextPath() + "/");
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
        UserDAO dao;
        User user = new User();
        HttpSession session = request.getSession();

        switch (request.getServletPath()) {
            case "/login":
                user.setEmail(request.getParameter("email"));
                user.setSenha(request.getParameter("senha"));

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = (UserDAO) daoFactory.getUserDAO();

                    dao.authenticate(user);

                    session.setAttribute("usuario", user);
                } catch (ClassNotFoundException | IOException | SQLException | SecurityException ex) {
                    session.setAttribute("error", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/");



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
