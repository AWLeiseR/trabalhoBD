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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Postagem;
import model.User;

/**
 *
 * @author Alan
 */
@WebServlet(
        name = "postagemController",
        urlPatterns = {
            "/posts",
            "/posts/create",
            "/posts/update",
            "/posts/delete"})
public class postagemController extends HttpServlet {

   

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
            DAO<Postagem> dao;
            Postagem post;
            RequestDispatcher dispatcher;
            switch (request.getServletPath()) {
                case "/posts": {
                    try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao = daoFactory.getDAO();

                        List<Postagem> postList = dao.all();
                        request.setAttribute("postList", postList);
                    } catch (ClassNotFoundException | IOException | SQLException ex) {
                        request.getSession().setAttribute("error", ex.getMessage());
                    }

                    dispatcher = request.getRequestDispatcher("/view/posts/index.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                case "/posts/create":{
                    dispatcher = request.getRequestDispatcher("/view/posts/create.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                case "/posts/update":{
                    try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao = daoFactory.getDAO();

                        post = dao.read(Integer.parseInt(request.getParameter("id")));
                        request.setAttribute("post", post);

                        dispatcher = request.getRequestDispatcher("/view/posts/update.jsp");
                    dispatcher.forward(request, response);
                    } catch (ClassNotFoundException | IOException | SQLException ex) {
                        request.getSession().setAttribute("error", ex.getMessage());
                        response.sendRedirect(request.getContextPath() + "/posts");
                    }

                    break;
                }
                case "/posts/delete":{
                    try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao = daoFactory.getDAO();
                        dao.delete(Integer.parseInt(request.getParameter("id")));

                        dispatcher = request.getRequestDispatcher("/view/posts/index.jsp");
                        dispatcher.forward(request, response);                    

                    } catch (ClassNotFoundException | IOException | SQLException ex) {
                        request.getSession().setAttribute("error", ex.getMessage());
                        response.sendRedirect(request.getContextPath() + "/posts");
                    }
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
           /* try {
                daoFactory = DAOFactory.getInstance();
                DAO<User> dao=daoFactory.getDAO();
                Postagem post =new Postagem();
                RequestDispatcher dispatcher;
                String servletPath = request.getServletPath();
                String nome= request.getParameter("titulo");
                if(!"".equals(nome)){
                    post.setNome(nome);
                }
                String sobrenome= request.getParameter("subtitulo");
                if(!"".equals(sobrenome)){
                    post.setSobrenome(sobrenome);
                }               
                String email= request.getParameter("email");
                if(!"".equals(email)){
                    post.setEmail(email);
                }

                String senha= request.getParameter("senha");
                if(!"".equals(senha)){
                    post.setSenha(senha);
                }
                String funcao= request.getParameter("funcao");
                if(!"".equals(funcao)){
                    post.setFuncao(funcao);
                }

                switch (request.getServletPath()) {

                    case "/posts/create":{

                        try {
                            Random generate= new Random();
                            int id=generate.nextInt(1000);
                            post.setUserId(id);

                            dao.create(post);

                        } catch (SQLException ex) {
                            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "/posts/update":{
                        try{
                            int userId; 
                            userId = Integer.valueOf(request.getParameter("postagemId"));

                           post.setUserId(userId);

                            dao.update(post);
                        } catch (SQLException ex) {
                            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }

                }
                 response.sendRedirect(request.getContextPath() + "/posts");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
            }*/

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
