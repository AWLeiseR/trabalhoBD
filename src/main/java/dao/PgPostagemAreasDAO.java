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

import model.PostagemAreas;
import model.UserAreas;

/**
 *
 * @author Alan
 */
public class PgPostagemAreasDAO implements DAO<PostagemAreas>{

    private final Connection connection; 
    
     PgPostagemAreasDAO(Connection connection) {
        this.connection = connection;
    }
    
    
    private static final String CREATE_QUERY =
                                "INSERT INTO revista.postagemareas(idpostagemareas,idpostagem,idareas) " +
                                "VALUES(?,?,?);";
     
     private static final String READ_QUERY_POSTAGEM =
                                "SELECT idpostagem,idareas " +
                                "FROM revista.postagemareas " +
                                "WHERE idpostagem = ?;";
     
     

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.postagemareas " +
                                "WHERE idpostagem = ?;";

    private static final String ALL_QUERY =
                                "SELECT idpostagemareas, idpostagem, idareas " +
                                "FROM revista.postagemareas " +
                                "ORDER BY idpostagem;";
    
        @Override
    public void create(PostagemAreas t) throws SQLException {
       try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)){
            
            statement.setInt(2, t.getIdPostagem());
            
            statement.setInt(3, t.getIdAreas());
            
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
    public PostagemAreas read(Integer id) throws SQLException {
        PostagemAreas postagemArea = new PostagemAreas();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY_POSTAGEM)) {
            statement.setInt(1, id);
            
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    postagemArea.setIdAreas(result.getInt("idareas"));
                    postagemArea.setIdPostagem(id);
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

        return postagemArea;
    }

    @Override
    public void update(PostagemAreas t) throws SQLException {
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

            if (ex.getMessage().equals("Erro ao excluir: area não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir area.");
            }
        }
    }

    @Override
    public List<PostagemAreas> all() throws SQLException {
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
