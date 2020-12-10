/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import model.Highlight;

/**
 *
 * @author Alan
 */
public interface HighlightDAO extends DAO<Highlight>{
    public int checkHighlight(int id, int idpostagem) throws SQLException;
    public void deleteHighlight(Integer id, Integer idPostagem) throws SQLException;
    
    
}
