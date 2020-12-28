/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AreasDAO;
import dao.DAO;
import dao.DAOFactory;
import dao.PostagemDAO;
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
import model.UserAreas;

/**
 *
 * @author Alan
 */
@WebServlet(
        name = "loginController",
        urlPatterns = {
            "",
            "/busca",
            "/area",
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
            AreasDAO daoAreas;
            PostagemDAO daoPostagem;
            DAO<UserAreas> daoUserAreas = null;
            User user;
            AreasDeInteresse area;
            
            int areaDeBusca = 1;
            int ordenarPor = 1;

            switch (request.getServletPath()) {
                case "": {
                    session = request.getSession(false);
                     try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                         daoPostagem = (PostagemDAO) daoFactory.getPostagemDAO();
                    if (session != null && session.getAttribute("usuario") != null) {
                        
                       
                           user = (User) session.getAttribute("usuario");
                            
                            daoAreas = (AreasDAO) daoFactory.getAreaDAO();
                            
                            daoUserAreas = (DAO<UserAreas>)  daoFactory.getUserAreasDAO();
                            
                            if(session.getAttribute("areaDeBusca") == null){
                                
                                
                                UserAreas userAreas = daoUserAreas.read(user.getUserId());
                                
                                areaDeBusca=userAreas.getIdAreas();
                                
                                request.setAttribute("areaDeBusca", areaDeBusca);
                            
                            }else{
                                areaDeBusca=(int) session.getAttribute("areaDeBusca");
                            }
                            
                            if(session.getAttribute("ordenarPor") == null){
                                
                                request.setAttribute("ordenarPor", ordenarPor);
                            
                            }
                            System.out.println(session.getAttribute("areaDeBusca"));
                            List<Postagem> postList = daoPostagem.postagemAreaUser(user.getUserId(),areaDeBusca, ordenarPor);
                            List<AreasDeInteresse> areasList = daoAreas.areasDoUser(user.getUserId());
                            
                            
                            
                            request.setAttribute("postList", postList);
                            request.setAttribute("areasList", areasList);
                            
                            dispatcher = request.getRequestDispatcher("/view/homePage.jsp");
                            
                        
                        }else{
                            List<Postagem> postList = daoPostagem.maisRecente();
                            request.setAttribute("postList", postList);
                            dispatcher = request.getRequestDispatcher("/view/index.jsp");
                        }
                        dispatcher.forward(request, response);
                    }catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    

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
        
        int ordenarPor=1;
        int areaDeBusca=0;

        switch (request.getServletPath()) {
            case "/login":{
                user.setEmail(request.getParameter("inputEmail"));
                user.setSenha(request.getParameter("inputSenha"));

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = (UserDAO) daoFactory.getUserDAO();

                    dao.authenticate(user);
                    
                    user.setFuncao(dao.getFuncao(user));
                    
                    session.setAttribute("usuario", user);
                    request.setAttribute("ordenarPor", 1);
                    
                } catch (ClassNotFoundException | IOException | SQLException | SecurityException ex) {
                    session.setAttribute("error", ex.getMessage());
                } 
                if(session.getAttribute("idPostagem")== null){
                    response.sendRedirect(request.getContextPath() + "");
                }else{
                    response.sendRedirect("/posts/read?id"+session.getAttribute("idPostagem"));
                }
                
                break;
            }
            case "/busca":{
               
                
                //areaDeBusca = Integer.parseInt(request.getParameter("area"));
                //System.out.println(Integer.parseInt(request.getParameter("order")));
                request.getSession().setAttribute("ordenarPor",Integer.parseInt(request.getParameter("order")));
                //request.setAttribute("ordenarPor", ordenarPor);
               request.getSession().setAttribute("areaDeBusca", Integer.parseInt(request.getParameter("area")));
                //System.out.println(session.getAttribute("areaDeBusca"));
               // request.setAttribute("areaDeBusca", Integer.parseInt(request.getParameter("area")));     

                 response.sendRedirect(request.getContextPath() + "");

                break;
            }

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
