/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.AuxReport;
import model.Visualizacoes;

/**
 *
 * @author Alan
 */
public interface VisualizacoesDAO extends DAO<Visualizacoes>{
    public List<AuxReport> getViewPerDay() throws SQLException;
    public int getTotalView()  throws SQLException;
}
