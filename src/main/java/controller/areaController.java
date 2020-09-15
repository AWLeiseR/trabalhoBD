/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAO;
import dao.DAOFactory;
import java.io.IOException;
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
import model.AreasDeInteresse;


/**
 *
 * @author Alan
 */
@WebServlet(
        name = "AreaController",
        urlPatterns = {
            "/areas",
            "/areas/create",
            "/areas/update",
            "/areas/delete"
           })
public class areaController extends HttpServlet {
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
        DAO<AreasDeInteresse> dao;
        AreasDeInteresse area;
        RequestDispatcher dispatcher;
        
        switch (request.getServletPath()) {
            case "/areas": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getAreaDAO();

                    List<AreasDeInteresse> areasList = dao.all();
                    request.setAttribute("areasList", areasList);
                    dispatcher = request.getRequestDispatcher("/view/areas/index.jsp");
                    dispatcher.forward(request, response);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }                
                break;
            }
            case "/areas/create":{
                dispatcher = request.getRequestDispatcher("/view/areas/create.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/areas/read":{
                 try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao = daoFactory.getAreaDAO();

                        area = dao.read(Integer.parseInt(request.getParameter("id")));
                        request.setAttribute("area", area);

                        dispatcher = request.getRequestDispatcher("/view/areas/update.jsp");
                    dispatcher.forward(request, response);
                    } catch (ClassNotFoundException | IOException | SQLException ex) {
                        request.getSession().setAttribute("error", ex.getMessage());
                        response.sendRedirect(request.getContextPath() + "/areas");
                    }

                    
                break;
            }
            
            case "/areas/update":{
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getAreaDAO();

                    area= dao.read(Integer.valueOf(request.getParameter("id")));
                    request.setAttribute("area", area);

                    dispatcher = request.getRequestDispatcher("/view/areas/update.jsp");
                    dispatcher.forward(request, response);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/areas");
                }
                
                break;
            }
            
            case "/areas/delete":{
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao = daoFactory.getAreaDAO();
                        dao.delete(Integer.valueOf(request.getParameter("id")));

                        dispatcher = request.getRequestDispatcher("/view/areas/index.jsp");
                        dispatcher.forward(request, response); 
                    } catch (ClassNotFoundException | IOException | SQLException ex) {
                        request.getSession().setAttribute("error", ex.getMessage());
                        response.sendRedirect(request.getContextPath() + "/areas");
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
                DAO<AreasDeInteresse> dao=daoFactory.getAreaDAO();
                AreasDeInteresse area=new AreasDeInteresse();
                RequestDispatcher dispatcher;
                
                String servletPath = request.getServletPath();
                
                String nome= request.getParameter("nome");
                
                if(!"".equals(nome)){
                    area.setNome(nome);
                }
                
                
                switch (request.getServletPath()) {

                    case "/areas/create":{

                        try {
                            Random generate= new Random();
                            int id=generate.nextInt(1000);
                            area.setAreaId(id);

                            dao.create(area);

                        } catch (SQLException ex) {
                            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "/areas/update":{
                        try{
                            int areaId; 
                            areaId = Integer.valueOf(request.getParameter("areaId"));

                           area.setAreaId(areaId);
                           System.out.println(areaId);
                            dao.update(area);
                        } catch (SQLException ex) {
                            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }

                }
                 response.sendRedirect(request.getContextPath() + "/areas");
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
