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
import model.AreasDeInteresse;
import java.util.Random;
import javax.servlet.http.HttpSession;
import model.UserAreas;

/**
 *
 * @author Alan
 */
@WebServlet(
        name = "UserController",
        urlPatterns = {
            "/user",
            "/user/create",
            "/user/read",
            "/user/update",
            "/user/delete",
        "/user/updatefunction"})
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
        UserDAO dao;
        DAO<AreasDeInteresse> daoAreas;
        User user;
        RequestDispatcher dispatcher;
         HttpSession session;
        switch (request.getServletPath()) {
            case "/user": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = (UserDAO) daoFactory.getUserDAO();

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
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    daoAreas = daoFactory.getAreaDAO();
                    
                    List<AreasDeInteresse> areasList = daoAreas.all();
                    
                    request.setAttribute("areasList", areasList);
                    
                    dispatcher = request.getRequestDispatcher("/view/user/create.jsp");
                    
                    dispatcher.forward(request, response);
                    
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "/user/read":{
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = (UserDAO) daoFactory.getUserDAO();

                    user = dao.read(Integer.parseInt(request.getParameter("id")));
                    request.setAttribute("user", user);

                    dispatcher = request.getRequestDispatcher("/view/user/read.jsp");
                dispatcher.forward(request, response);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user");
                }
                break;
            }
            case "/user/update":{
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = (UserDAO) daoFactory.getUserDAO();
                    session = request.getSession();
                    user = (User) session.getAttribute("usuario");
                    System.out.println(user.getUserId());
                    System.out.println(Integer.parseInt(request.getParameter("id")));
                    if(user.getUserId() == Integer.parseInt(request.getParameter("id"))){
                        user = dao.read(Integer.parseInt(request.getParameter("id")));
                        request.setAttribute("user", user);
                        dispatcher = request.getRequestDispatcher("/view/user/update.jsp");
                    }else{
                        String funcao = dao.getFuncao(Integer.parseInt(request.getParameter("id"))) ;
                       request.setAttribute("id",Integer.parseInt(request.getParameter("id")) );
                        request.setAttribute("funcao",funcao );
                       dispatcher = request.getRequestDispatcher("/view/user/updateFunction.jsp"); 
                    }
                    
                    

                    
                dispatcher.forward(request, response);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user");
                }
                
                break;
            }
            case "/user/delete":{
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = (UserDAO) daoFactory.getUserDAO();
                    dao.delete(Integer.valueOf(request.getParameter("id")));
                    
                    dispatcher = request.getRequestDispatcher("/view/user");
                    dispatcher.forward(request, response);                    

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user");
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
                UserDAO dao=(UserDAO) daoFactory.getUserDAO();
                DAO<UserAreas> daoAreas = daoFactory.getUserAreasDAO();
                User user=new User();
                UserAreas userAreas = new UserAreas();
                RequestDispatcher dispatcher;
                
                String servletPath = request.getServletPath();
                
                String nome= request.getParameter("nome");
                if(!"".equals(nome)){
                    user.setNome(nome);
                }
                String sobrenome= request.getParameter("sobrenome");
                if(!"".equals(sobrenome)){
                    user.setSobrenome(sobrenome);
                }               
                String email= request.getParameter("email");
                if(!"".equals(email)){
                    user.setEmail(email);
                }

                String senha = request.getParameter("senha");
                
                if(!"".equals(senha)){
                    user.setSenha(senha);
                }
                String funcao = request.getParameter("funcao");
                if(!"".equals(funcao)){
                    user.setFuncao(funcao);
                }
                
                String area = request.getParameter("areas");
                 //System.out.println(request.getParameter("areas"));
                //System.out.println(!"".equals(area) && area != null);
                if(!"".equals(area) && area != null){
                    System.out.println(area);
                    userAreas.setIdAreas(Integer.valueOf(area));
                    
                }
                

                switch (request.getServletPath()) {

                    case "/user/create":{

                        try {
                            Random generate= new Random();
                            int id=generate.nextInt(1000);
                            user.setUserId(id);
                            userAreas.setIdUser(id);
                            
                            dao.create(user);
                            
                            daoAreas.create(userAreas);
                            

                        } catch (SQLException ex) {
                            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "/user/update":{
                        try{
                            int userId; 
                            userId = Integer.valueOf(request.getParameter("userId"));

                           user.setUserId(userId);

                            dao.update(user);
                        } catch (SQLException ex) {
                            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "/user/updatefunction":{
                        try{
                            int userId; 
                            userId = Integer.valueOf(request.getParameter("userId"));

                            dao.updateFuncao(userId, funcao);
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
