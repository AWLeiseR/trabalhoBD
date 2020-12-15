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
                                "INSERT INTO revista.postagemareas(idpostagem,idareas) " +
                                "VALUES(?,?);";
     
     private static final String READ_QUERY_POSTAGEM =
                                "SELECT idpostagem,idareas " +
                                "FROM revista.postagemareas " +
                                "WHERE idpostagem = ?;";
     
    private static final String UPDATE_QUERY =
                                "UPDATE revista.postagemareas " +
                                "SET idareas=? " +
                                "WHERE idpostagem = ?;"; 

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.postagemareas " +
                                "WHERE idpostagem = ?;";

    private static final String ALL_QUERY =
                                "SELECT idpostagem, idareas " +
                                "FROM revista.postagemareas " +
                                "ORDER BY idpostagem;";
    
        @Override
    public void create(PostagemAreas t) throws SQLException {
       try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)){
            
            statement.setInt(1, t.getIdPostagem());
            
            statement.setInt(2, t.getIdAreas());
            
            statement.executeUpdate();
        }catch(SQLException ex) {
             Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir postagemArea: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir postagemArea.");
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
                    //throw new SQLException("Erro ao visualizar: PostagemArea não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: PostagemArea não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar PostagemArea.");
            }
        }

        return postagemArea;
    }

    @Override
    public void update(PostagemAreas t) throws SQLException {
  
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            
            statement.setInt(1, t.getIdAreas());
            statement.setInt(2, t.getIdPostagem());
            
            
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: postagem não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: postagem não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar a postagem: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar postagem.");
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
         try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: PostagemArea não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: PostagemArea não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir PostagemArea.");
            }
        }
    }

    @Override
    public List<PostagemAreas> all() throws SQLException {
        List<PostagemAreas> postagemAreaList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
            ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                PostagemAreas area = new PostagemAreas();
                area.setIdPostagem(result.getInt("IdPostagem"));
                area.setIdAreas(result.getInt("idAreas"));

                postagemAreaList.add(area);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAreaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar PostagemAreas.");
        }

        return postagemAreaList;
    }
    
    
    
}
