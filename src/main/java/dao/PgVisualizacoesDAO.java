/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Visualizacoes;

/**
 *
 * @author Alan
 */
public class PgVisualizacoesDAO implements VisualizacoesDAO {
     private final Connection connection;
    
    public PgVisualizacoesDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_QUERY =
                                "INSERT INTO revista.visualizacoes (idUser,idPostagem, visualizacaodata) " +
                                "VALUES(?,?,?);";

    private static final String READ_QUERY =
                                "SELECT visualizacaodata " +
                                "FROM revista.visualizacoes " +
                                "WHERE iduser = ? AND idPostagem;";

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.visualizacoes " +
                                "WHERE iduser = ? AND idpostagem = ? AND visualizacaodata = ?;";

    private static final String ALL_QUERY =
                                "SELECT idUser,idPostagem, visualizacaodata " +
                                "FROM revista.visualizacoes " +
                                "ORDER BY idPostagem;";
      
    @Override
    public void create(Visualizacoes t) throws SQLException {
       try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            
            statement.setInt(1, t.getIdUser());            
            statement.setInt(2, t.getIdPostagem());
            statement.setDate(3,t.getVisualizacaoData());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
        }
    }

    @Override
    public Visualizacoes read(Integer id) throws SQLException {
         Visualizacoes v = new Visualizacoes();
        try(PreparedStatement statement = connection.prepareStatement(READ_QUERY)){
             statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    v.setIdUser(result.getInt("idUser"));
                    v.setIdPostagem(result.getInt("idPostagem"));
                    v.setVisualizacaoData(result.getDate("visualizacaoData"));
                    
                } else {
                    throw new SQLException("Erro ao visualizar: visualizacao não encontrado.");
                }
            }
        }catch (SQLException ex){
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

        }
        return v;
    }

    @Override
    public void update(Visualizacoes t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Visualizacoes> all() throws SQLException {
        List<Visualizacoes> vList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Visualizacoes v = new Visualizacoes();
                v.setIdUser(result.getInt("iduser"));
                v.setIdPostagem(result.getInt("idpostagem"));
               vList.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar high.");
        }

        return vList;
    }
    
}
