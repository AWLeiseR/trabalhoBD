/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAO;
import dao.DAOFactory;
import dao.PostagemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Postagem;


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
            "/posts/read",
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
            PostagemDAO daoPost;
            Postagem post;
            RequestDispatcher dispatcher;
            int visualizacoes;
           
            switch (request.getServletPath()) {
                case "/posts": {
                    try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao = daoFactory.getPostagemDAO();

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
                        dao = daoFactory.getPostagemDAO();

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
                
                case "/posts/read":{
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getPostagemDAO();
                    
                    daoPost = (PostagemDAO) daoFactory.getPostagemDAO();
                    
                    visualizacoes = daoPost.numeroVisualizacoes(Integer.valueOf(request.getParameter("id")));
                    
                    visualizacoes++;
                    
                    daoPost.setNumeroVizualizacoes(Integer.valueOf(request.getParameter("id")), visualizacoes);
                    
                    post = dao.read(Integer.valueOf(request.getParameter("id")));
                    request.setAttribute("post", post);

                    dispatcher = request.getRequestDispatcher("/view/posts/read.jsp");
                    dispatcher.forward(request, response);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/posts");
                }
                break;
            }
                
                case "/posts/delete":{
                    try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao = daoFactory.getPostagemDAO();
                        dao.delete(Integer.parseInt(request.getParameter("id")));

                        dispatcher = request.getRequestDispatcher("/view/posts");
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
            try {
                daoFactory = DAOFactory.getInstance();
                DAO<Postagem> dao=daoFactory.getPostagemDAO();
                Postagem post =new Postagem();
                RequestDispatcher dispatcher;
                String servletPath = request.getServletPath();
                String titulo= request.getParameter("titulo");
                if(!"".equals(titulo)){
                    System.out.println(titulo);
                    post.setTitulo(titulo);
                }
                String subtitulo= request.getParameter("subtitulo");
                if(!"".equals(subtitulo)){
                    post.setSubtitulo(subtitulo);
                }               
                String descricao= request.getParameter("descricao");
                if(!"".equals(descricao)){
                    post.setDescricao(descricao);
                }

                String conteudo= request.getParameter("conteudo");
                if(!"".equals(conteudo)){
                    post.setConteudo(conteudo);
                }
                String area= request.getParameter("area");
                if(!"".equals(area)){
                    //post.setArea(area);
                }

                switch (request.getServletPath()) {

                    case "/posts/create":{

                        try {
                            Random generate= new Random();
                            
                            int id=generate.nextInt(1000);
                            
                            post.setPostagemId(id);
                            
                            long millis=System.currentTimeMillis();  
                            
                            java.sql.Date data = new java.sql.Date(millis);  
                            
                            post.setCreateAt(data);
                            
                            post.setAlteradoAt(data);
                            
                            dao.create(post);

                        } catch (SQLException ex) {
                            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "/posts/update":{
                        try{
                            int postagemId; 
                            postagemId = Integer.valueOf(request.getParameter("postagemId"));

                           post.setPostagemId(postagemId);

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
