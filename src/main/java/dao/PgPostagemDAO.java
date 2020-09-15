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
import model.Postagem;

/**
 *
 * @author Alan
 *//**
 *
 * @author Alan
 */
public class PgPostagemDAO implements DAO<Postagem>  {
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO revista.users(userid, nome, sobrenome, email, senha, funcao) " +
                                "VALUES(?,?,?,?, md5(?),?);";

    private static final String READ_QUERY =
                                "SELECT nome, sobrenome, email,funcao " +
                                "FROM revista.users " +
                                "WHERE userId = ?;";

    private static final String UPDATE_QUERY =
                                "UPDATE revista.users " +
                                "SET nome= ?,  sobrenome= ?, email= ?, funcao=?  " +
                                "WHERE userid = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.users " +
                                "WHERE userid = ?;";

    private static final String ALL_QUERY =
                                "SELECT userid, nome " +
                                "FROM revista.users " +
                                "ORDER BY userid;";

   /* private static final String AUTHENTICATE_QUERY =
                                "SELECT id, pnome, snome " +
                                "FROM j2ee.user " +
                                "WHERE email = ? AND senha = md5(?);";*/

    public PgPostagemDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Postagem t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            
            statement.setInt(1, t.getPostagemId());
            statement.setString(2, t.getTitulo());
            statement.setString(3, t.getSubtitulo());
            statement.setString(4,t.getDescricao());
            statement.setString(5, t.getConteudo());
            statement.setInt(6, t.getVisualizacoes());
            statement.setDate(7, t.getCreateAt());
            statement.setDate(7, t.getCreateAt());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

             if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir usuário.");
            }
        }
    }

    @Override
    public Postagem read(Integer id) throws SQLException {
         Postagem post = new Postagem();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    post.setPostagemId(id);
                    post.setTitulo(result.getString("titulo"));
                    post.setSubtitulo(result.getString("subtitulo"));
                    post.setDescricao(result.getString("descricao"));
                    post.setConteudo(result.getString("conteudo"));
                    post.setVisualizacoes(result.getInt("visualizacoes"));
                    post.setCreateAt(result.getDate("createat"));
                    post.setAlteradoAt(result.getDate("alteradoat"));
                    
                } else {
                    throw new SQLException("Erro ao visualizar: usuário não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: postagem não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar postagem.");
            }
        }

        return post;
    }

    @Override
    public void update(Postagem t) throws SQLException {
        String query;
            
                query = UPDATE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, t.getTitulo());
            statement.setString(2, t.getSubtitulo());
            statement.setString(3, t.getConteudo());
            statement.setString(4, t.getDescricao());
            statement.setInt(4, t.getVisualizacoes());
            statement.setDate(4, t.getAlteradoAt());
            statement.setInt(5, t.getPostagemId());
              
            

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
                throw new SQLException("Erro ao excluir: postagem não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: postagem não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir postagem.");
            }
        }
    }

    @Override
    public List<Postagem> all() throws SQLException {
        List<Postagem> postList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Postagem post = new Postagem();
                post.setPostagemId(result.getInt("postId"));
                post.setTitulo(result.getString("titulo"));

                postList.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return postList;
    }
    
}
