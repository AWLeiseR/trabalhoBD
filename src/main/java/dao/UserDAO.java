/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.User;
import  model.AuxReport;

/**
 *
 * @author Alan
 */
public interface UserDAO extends DAO<User> {
    public void authenticate(User usuario) throws SQLException, SecurityException;
    public User getByLogin(String login) throws SQLException;
    public String getFuncao(User user) throws SQLException;
    public String getFuncao(int id) throws SQLException;
    public void updateFuncao(int id,String funcao) throws SQLException;
    public int getId(String email) throws SQLException;
    public List<AuxReport> getUserPerDay() throws SQLException;
    public int getTotalUser()  throws SQLException;
    public double getAverageUser() throws SQLException;
    public List<AuxReport> getUserScore() throws SQLException;
}
