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
public class PgAreaDAO implements AreasDAO  {

    private final Connection connection;
 
    
    public PgAreaDAO(Connection connection) {
        this.connection = connection;
    }
    
     private static final String CREATE_QUERY =
                                "INSERT INTO revista.areas(areasid, nome) " +
                                "VALUES(?,?);";
     
     private static final String READ_QUERY =
                                "SELECT nome " +
                                "FROM revista.areas " +
                                "WHERE areasid = ?;";
     
     private static final String UPDATE_QUERY =
                                "UPDATE revista.areas " +
                                "SET nome= ?" +
                                "WHERE areasid = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.areas " +
                                "WHERE areasid = ?;";

    private static final String ALL_QUERY =
                                "SELECT areasid, nome " +
                                "FROM revista.areas " +
                                "ORDER BY areasid;";
    
    private static final String AREAS_DO_USER=
                                "select nome, areasid "+
                                "from revista.areas, revista.userareas "+
                                "where iduser = ? and idareas = areasid;";
    
    @Override
    public void create(AreasDeInteresse t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            
            statement.setInt(1, t.getAreaId());
            
            statement.setString(2, t.getNome());
            
            
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
        AreasDeInteresse area = new AreasDeInteresse();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    area.setNome(result.getString("nome"));
                    area.setAreaId(id);
                } else {
                    throw new SQLException("Erro ao visualizar: area não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: area não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar area.");
            }
        }

        return area;
    }

    @Override
    public void update(AreasDeInteresse t) throws SQLException {
        String query;
            
                query = UPDATE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, t.getNome());
            statement.setInt(2, t.getAreaId());
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: area não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: area não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar a area: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar area.");
            }
        }
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

            if (ex.getMessage().equals("Erro ao excluir: area não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir area.");
            }
        }
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

    @Override
    public List<AreasDeInteresse> areasDoUser(int id) throws SQLException {
         List<AreasDeInteresse> areaList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(AREAS_DO_USER)){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery(); 
            while (result.next()) {
                AreasDeInteresse area = new AreasDeInteresse();
                area.setAreaId(result.getInt("areasId"));
                area.setNome(result.getString("nome"));

                areaList.add(area);
            }
        }catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar areas de interesse do User.");
        }

        return areaList;
    }
    
}
