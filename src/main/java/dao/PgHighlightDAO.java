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
import model.Highlight;
import model.Postagem;

/**
 *
 * @author Alan
 */
public class PgHighlightDAO implements DAO<Highlight> {
    
    private final Connection connection;
    
    public PgHighlightDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_QUERY =
                                "INSERT INTO revista.highlight (idUser,idPostagem) " +
                                "VALUES(?,?);";

    private static final String READ_QUERY =
                                "SELECT idPostagem " +
                                "FROM revista.highlight " +
                                "WHERE idUser = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.highlight " +
                                "WHERE idUser = ? AND idPostagem = ? ;";

    private static final String ALL_QUERY =
                                "SELECT idPostagem, idUser " +
                                "FROM revista.highlight " +
                                "ORDER BY idPostagem;";
    
    private static final String HIGHLIGHT_DE_UMA_POSTAGEM = 
                                "SELECT idPostagem, idUser " +
                                "FROM revista.highlight " +
                                "WHERE idUser = ? AND idPostagem = ? ;";

    @Override
    public void create(Highlight t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            
           
            statement.setInt(1, t.getIdUser());
            statement.setInt(2, t.getIdPostagem());
            

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
        }
    }

    @Override
    public Highlight read(Integer id) throws SQLException {
        Highlight high = new Highlight();
        try(PreparedStatement statement = connection.prepareStatement(READ_QUERY)){
             statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    high.setIdUser(result.getInt("idUser"));
                    high.setIdPostagem(result.getInt("idPostagem"));
                    
                } else {
                    throw new SQLException("Erro ao visualizar: postagem não encontrado.");
                }
            }
        }catch (SQLException ex){
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

        }
        return high;
    }

    @Override
    public void update(Highlight t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void deleteHighlight(Integer id, Integer idPostagem) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            statement.setInt(2, idPostagem);
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: highlight não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: highlight não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir highlight.");
            }
        }
    }

    @Override
    public List<Highlight> all() throws SQLException {
        List<Highlight> highList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Highlight high = new Highlight();
                high.setIdUser(result.getInt("idUser"));
                high.setIdPostagem(result.getInt("idPostagem"));
               highList.add(high);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar high.");
        }

        return highList;
    }
    
    public int checkHighlight(int id, int idpostagem) throws SQLException {
        Highlight high = new Highlight();
        try(PreparedStatement statement = connection.prepareStatement(READ_QUERY)){
             statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    high.setIdUser(result.getInt("idUser"));
                    high.setIdPostagem(result.getInt("idPostagem"));
                    return 1;
                } else {
                    throw new SQLException("Erro ao visualizar: postagem não encontrado.");
                }
            }
        }catch (SQLException ex){
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

        }
        return 0;
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}


 
