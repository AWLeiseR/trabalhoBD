/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AreasDeInteresse;

/**
 *
 * @author Alan
 */
public class PgAreaDAO implements DAO<AreasDeInteresse>  {

    private final Connection connection;
    
    public PgAreaDAO(Connection connection) {
        this.connection = connection;
    }
    
     private static final String CREATE_QUERY =
                                "INSERT INTO revista.areas(areasid, nome) " +
                                "VALUES(?,?);";
     
     private static final String READ_QUERY =
                                "SELECT nome, " +
                                "FROM revista.areas " +
                                "WHERE areasid = ?;";
     
     private static final String UPDATE_QUERY =
                                "UPDATE revista.areas " +
                                "SET nome= ?,   " +
                                "WHERE areasid = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.areas " +
                                "WHERE areasid = ?;";

    private static final String ALL_QUERY =
                                "SELECT areasid, nome " +
                                "FROM revista.areas " +
                                "ORDER BY areasid;";
     
    
    
    @Override
    public void create(AreasDeInteresse t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            
            statement.setInt(1, t.getAreaId());
            System.out.println(t.getAreaId());
            statement.setString(2, t.getNome());
            System.out.println(t.getNome());
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir area: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir area.");
            }
        }
    }

    @Override
    public AreasDeInteresse read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(AreasDeInteresse t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AreasDeInteresse> all() throws SQLException {
        List<AreasDeInteresse> areaList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
            ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                AreasDeInteresse area = new AreasDeInteresse();
                area.setAreaId(result.getInt("areasId"));
                area.setNome(result.getString("nome"));

                areaList.add(area);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar areas de interesse.");
        }

        return areaList;
    }
    
}
