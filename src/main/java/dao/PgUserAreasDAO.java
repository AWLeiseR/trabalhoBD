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
import model.UserAreas;

/**
 *
 * @author Alan
 */
public class PgUserAreasDAO implements UserAreasDAO {

    private final Connection connection; 
    
    PgUserAreasDAO(Connection connection) {
        this.connection = connection;
    }
    
    private static final String CREATE_QUERY =
                                "INSERT INTO revista.userareas(iduser,idareas) " +
                                "VALUES(?,?);";
     
     private static final String READ_QUERY_USER =
                                "SELECT iduser,idareas " +
                                "FROM revista.userareas " +
                                "WHERE iduser = ? LIMIT 1;";

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.userareas " +
                                "WHERE iduser = ? ;";

    private static final String ALL_QUERY =
                                "SELECT  iduser,idareas " +
                                "FROM revista.userareas " +
                                "ORDER BY iduser;";
    
    private static final String ALL_AREAS_USER_QUERY =
                                "SELECT idareas " +
                                "FROM revista.userareas " +
                                "WHERE iduser = ?;";
    
    private static final String DELETE_QUERY_AREA =
                                "DELETE FROM revista.userareas " +
                                "WHERE iduser = ? and idareas = ?;";

    @Override
    public void create(UserAreas t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)){
            System.out.println("entrou");
            statement.setInt(1, t.getIdUser());
            
            statement.setInt(2, t.getIdAreas());
            
            statement.executeUpdate();
        }catch(SQLException ex) {
             Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir UserArea: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir UserArea.");
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
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: area não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: area do user não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir area do user.");
            }
        }
    }

    @Override
    public List<UserAreas> all() throws SQLException {
        List<UserAreas> userAreaList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
            ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                UserAreas area = new UserAreas();
                area.setIdAreas(result.getInt("idAreas"));
                area.setIdUser(result.getInt("idUser"));

                userAreaList.add(area);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar areas e users.");
        }

        return userAreaList;
    }

    @Override
    public List<UserAreas> todasAreasUser(Integer id) throws SQLException {
         List<UserAreas> userAreaList = new ArrayList<>();
         
        try (PreparedStatement statement = connection.prepareStatement(ALL_AREAS_USER_QUERY)) {
            statement.setInt(1, id);
            
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    UserAreas area = new UserAreas();
                    area.setIdAreas(result.getInt("idAreas"));

                    userAreaList.add(area);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar areas e users.");
        }

        return userAreaList;
    }

    @Override
    public void deleteArea(UserAreas ua) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY_AREA)) {
            statement.setInt(1, ua.getIdUser());
            statement.setInt(2, ua.getIdAreas());
            System.out.println(ua.getIdUser());
            System.out.println(ua.getIdAreas());
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: area não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: area do user não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir area do user.");
            }
        }
    }
    
}
