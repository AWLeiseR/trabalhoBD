/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAO;
import dao.DAOFactory;
import dao.HighlightDAO;
import dao.PostagemAreasDAO;
import dao.PostagemDAO;
import dao.VisualizacoesDAO;
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
import javax.servlet.http.HttpSession;
import model.AreasDeInteresse;
import model.Highlight;
import model.Postagem;
import model.PostagemAreas;
import model.User;
import model.Visualizacoes;


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
            "/posts/delete",
            "/posts/login",
            "/posts/tirarHighlight",
            "/posts/highlight"})
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
            DAO<AreasDeInteresse> daoArea = null;
            PostagemDAO daoPost;
            Postagem post;
            DAO<PostagemAreas> daoPostArea;
            PostagemAreas postArea;
            User user = null;
            HighlightDAO daoHigh;
            RequestDispatcher dispatcher;
            int visualizacoes;
            HttpSession session = null;
            Integer aux;
            VisualizacoesDAO daoViews;
            Visualizacoes visualizacao = null;
            long d = System.currentTimeMillis();
            Date date = new Date(d);
           
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
                    try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        daoArea = daoFactory.getAreaDAO();
                        List<AreasDeInteresse> areaList = daoArea.all();
                        
                        request.setAttribute("areasList", areaList);
                       dispatcher = request.getRequestDispatcher("/view/posts/create.jsp");
                       dispatcher.forward(request, response);
                    
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(postagemController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   break;  
                }
                case "/posts/update":{
                    try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao = daoFactory.getPostagemDAO();
                        daoArea = daoFactory.getAreaDAO();
                        daoPostArea =  daoFactory.getPostagemAreasDAO();
                        
                        postArea = daoPostArea.read(Integer.parseInt(request.getParameter("id")));
                        
                        try{
                            List<AreasDeInteresse> areaList = daoArea.all();
                            request.setAttribute("areasList", areaList);
                        }catch(SQLException ex){
                            
                        }
                        
                        
                        post = dao.read(Integer.parseInt(request.getParameter("id")));
                        
                        request.setAttribute("areaDeBusca", postArea.getIdAreas());
                        
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
                    visualizacao = new Visualizacoes();
                    daoPost = (PostagemDAO) daoFactory.getPostagemDAO();
                    daoViews = (VisualizacoesDAO) daoFactory.getVisualizacoesDAO();
                         
                    session = request.getSession();
                    if (session != null && session.getAttribute("usuario") != null) {
                        user = (User) session.getAttribute("usuario");
                        daoHigh = (HighlightDAO) daoFactory.getHighlightDAO();
                        aux = daoHigh.checkHighlight(user.getUserId(), Integer.valueOf(request.getParameter("id")));
                        visualizacao.setIdUser(user.getUserId());
                        session.setAttribute("usuario",user);
                        request.setAttribute("aux",aux);
                        List<Postagem> relatedPost = daoPost.getRelatedPost(Integer.valueOf(request.getParameter("id")));
                        request.setAttribute("relatedPost", relatedPost);
                    }
                    visualizacao.setIdPostagem(Integer.valueOf(request.getParameter("id")));
                    
                    visualizacao.setVisualizacaoData(date);
                    
                    daoViews.create(visualizacao);
                    int views = daoViews.getViewPost(Integer.valueOf(request.getParameter("id")));
                    post = dao.read(Integer.valueOf(request.getParameter("id")));
                    post.setVisualizacoes(views);
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
            
            case "/posts/login":{
                request.setAttribute("idPostagem",Integer.valueOf(request.getParameter("id")));
                
                response.sendRedirect(request.getContextPath() + "/login");
                break;
            }
            case "/posts/highlight":{
                
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        user = new User();
                        int postagemId=Integer.valueOf(request.getParameter("idPostagem"));
                        int userId=Integer.valueOf(request.getParameter("id"));
                        daoHigh =  (HighlightDAO) daoFactory.getHighlightDAO();
                        Highlight high = new Highlight();
                        
                        long millis=System.currentTimeMillis();  
                            
                        java.sql.Date data = new java.sql.Date(millis);
                        high.setIdPostagem(postagemId);
                       // System.out.println(Integer.valueOf(request.getParameter("idPostagem")));
                        high.setIdUser(userId);
                        high.setHighlightDate(data);
                        daoHigh.create(high);
                        user.setUserId(userId);
                        //session.setAttribute("usuario",user);
                        response.sendRedirect(request.getContextPath() + "/posts/read?id="+request.getParameter("idPostagem"));
                        
                    
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(postagemController.class.getName()).log(Level.SEVERE, null, ex);
                }
               break;
            }
            case "/posts/tirarHighlight":{
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                     
                        
                        daoHigh =  (HighlightDAO) daoFactory.getHighlightDAO();
                        
                        daoHigh.deleteHighlight(Integer.valueOf(request.getParameter("id")),Integer.valueOf(request.getParameter("idPostagem")) );
                        
                        dispatcher = request.getRequestDispatcher("/posts/read?id="+request.getParameter("idPostagem"));
                        dispatcher.forward(request, response);
                    
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(postagemController.class.getName()).log(Level.SEVERE, null, ex);
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
        HttpSession session = null;
        Postagem post =new Postagem();
        RequestDispatcher dispatcher;
            try {
                daoFactory = DAOFactory.getInstance();
                PostagemDAO dao=(PostagemDAO)daoFactory.getPostagemDAO();
                DAO<PostagemAreas> daoPostArea = daoFactory.getPostagemAreasDAO();
                
                
                String servletPath = request.getServletPath();
                
                PostagemAreas postArea = new PostagemAreas();
                
                String titulo= request.getParameter("titulo");
                if(!"".equals(titulo)){
                    
                    post.setTitulo(titulo);
                }
                String subtitulo = request.getParameter("subtitulo");
                if(!"".equals(subtitulo)){
                    post.setSubtitulo(subtitulo);
                }               
                String descricao = request.getParameter("descricao");
                if(!"".equals(descricao)){
                    post.setDescricao(descricao);
                }

                String conteudo = request.getParameter("conteudo");
                if(!"".equals(conteudo)){
                    post.setConteudo(conteudo);
                }
                int area = Integer.valueOf(request.getParameter("area"));
                if(!"".equals(area)){
                    postArea.setIdAreas(area);
                    
                }
                session = request.getSession();
                
                if(session.getAttribute("usuario") != null){
                    User user = (User)  session.getAttribute("usuario");
                    post.setAutor(user.getUserId());
                }
                
                
                 
                 
                switch (request.getServletPath()) {

                    case "/posts/create":{

                        try {
                            int id=0;
                            long millis=System.currentTimeMillis();  
                            
                            java.sql.Date data = new java.sql.Date(millis);  
                            
                            post.setCreateAt(data);
                            
                            post.setAlteradoAt(data);
                            
                            dao.create(post);
                           
                            postArea.setIdPostagem(dao.getPostId(post.getTitulo()));
                            
                            
                            daoPostArea.create(postArea);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "/posts/update":{
                        try{
                            int postagemId; 
                            postagemId = Integer.valueOf(request.getParameter("postagemId"));
                            postArea.setIdPostagem(postagemId);
                            post.setPostagemId(postagemId);
                            try{
                              daoPostArea.update(postArea);  
                            }catch(SQLException ex){
                                daoPostArea.create(postArea);
                            }
                            
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
