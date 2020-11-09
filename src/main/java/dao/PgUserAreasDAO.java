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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UserAreas;

/**
 *
 * @author Alan
 */
public class PgUserAreasDAO implements DAO<UserAreas> {

    private final Connection connection; 
    
    PgUserAreasDAO(Connection connection) {
        this.connection = connection;
    }
    
    private static final String CREATE_QUERY =
                                "INSERT INTO revista.userareas(iduserareas,iduser,idareas) " +
                                "VALUES(?,?);";
     
     private static final String READ_QUERY_USER =
                                "SELECT iduser,idareas " +
                                "FROM revista.userareas " +
                                "WHERE iduser = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.userareas " +
                                "WHERE iduser = ?;";

    private static final String ALL_QUERY =
                                "SELECT iduserareas, iduser,idareas " +
                                "FROM revista.userareas " +
                                "ORDER BY iduser;";

    @Override
    public void create(UserAreas t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)){
            statement.setInt(1, t.getIdUser());
            
            statement.setInt(2, t.getIdAreas());
            
            statement.executeUpdate();
        }catch(SQLException ex) {
             Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir area: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir area.");
            }
        }
    }

    @Override
    public UserAreas read(Integer id) throws SQLException {
         UserAreas userArea = new UserAreas();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY_USER)) {
            statement.setInt(1, id);
            
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    userArea.setIdAreas(result.getInt("idareas"));
                    userArea.setIdUser(id);
                } else {
                    throw new SQLException("Erro ao visualizar: userArea não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: userArea não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar userArea.");
            }
        }

        return userArea;
    }

    @Override
    public void update(UserAreas t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserAreas> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
