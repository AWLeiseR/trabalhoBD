/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.AreasDeInteresse;

/**
 *
 * @author Alan
 */
public interface AreasDAO extends DAO<AreasDeInteresse> {
   public List<AreasDeInteresse> areasDoUser(int id) throws SQLException;
}
