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
public class PgPostagemDAO implements PostagemDAO  {
    
    private final Connection connection;
    private Object statement;
    
    public PgPostagemDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_QUERY =
                                "INSERT INTO revista.postagem(titulo,subtitulo,descricao,conteudo,visualizacoes,createAt,alteradoAt,autor) " +
                                "VALUES(?,?,?,?,?,?,?,?);";

    private static final String READ_QUERY =
                                "SELECT titulo,subtitulo,descricao,conteudo,visualizacoes,createAt,alteradoAt " +
                                "FROM revista.postagem " +
                                "WHERE postagemid = ?;";

    private static final String UPDATE_QUERY =
                                "UPDATE revista.postagem " +
                                "SET titulo= ?,  subtitulo= ?, descricao=?,conteudo=?,visualizacoes=?,alteradoAt=? " +
                                "WHERE postagemid = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.postagem " +
                                "WHERE postagemid = ?;";

    private static final String ALL_QUERY =
                                "SELECT postagemid, titulo " +
                                "FROM revista.postagem " +
                                "ORDER BY postagemid;";
    
    private static final String POSTAGENS_RECENTES=
                                 "SELECT postagemid, titulo, subtitulo, descricao  " +
                                 "FROM revista.postagem " +
                                 "ORDER BY createAt DESC LIMIT 3;";
    
    private static final String GET_NUMERO_VIUS=
                                "SELECT visualizacoes " +
                                "FROM revista.postagem "+
                                "WHERE postagemid=?;";
    
    private static final String UPDATE_NUMERO_VISUALIZACOES =
                                "UPDATE revista.postagem " +
                                "SET visualizacoes=?" +
                                "WHERE postagemid = ?;";
    
    private static final String POSTAGENS_MAIS_VISTAS=
                                "SELECT postagemid, titulo, subtitulo, descricao " +
                                "FROM revista.postagem "+
                                "ORDER BY visualizacoes DESC LIMIT 3;";
    
    private static final String POSTAGENS_AREA_DO_USER = 
                                    "select postagemid,titulo,subtitulo,descricao " +
                                    "from revista.userareas, revista.postagemareas, revista.postagem " + 
                                    "where iduser = ? and postagemareas.idareas = ? AND postagem.postagemid = postagemareas.idpostagem "+
                                    "order by ? Limit 3;";
    
    private static final String ID_POSTAGEM =
                                "select postagemid" +
                                " FROM revista.postagem " +
                                "WHERE titulo= ?;";

    @Override
    public void create(Postagem t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            
            statement.setString(1, t.getTitulo());
            statement.setString(2, t.getSubtitulo());
            statement.setString(3,t.getDescricao());
            statement.setString(4, t.getConteudo());
            statement.setInt(5, t.getVisualizacoes());
            statement.setDate(6, t.getCreateAt());
            statement.setDate(7, t.getAlteradoAt());
            statement.setInt(8,t.getAutor());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

             if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir postagem: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir postagem.");
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
                    throw new SQLException("Erro ao visualizar: postagem não encontrado.");
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
            statement.setString(3, t.getDescricao());
            statement.setString(4, t.getConteudo());
            statement.setInt(5, t.getVisualizacoes());
            statement.setDate(6, t.getAlteradoAt());
            statement.setInt(7, t.getPostagemId());
            
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
                post.setPostagemId(result.getInt("postagemId"));
                post.setTitulo(result.getString("titulo"));
                postList.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return postList;
    }
    
    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Postagem> maisRecente() throws SQLException {
       List<Postagem> postList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(POSTAGENS_RECENTES);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Postagem post = new Postagem();
                post.setPostagemId(Integer.valueOf(result.getString("postagemid")));
                post.setTitulo(result.getString("titulo"));
                post.setSubtitulo(result.getString("subtitulo"));
                post.setDescricao(result.getString("descricao"));               
                postList.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar Postagens.");
        }

        return postList;
    }

    @Override
    public int numeroVisualizacoes(int id) throws SQLException {
         int visualizacoes ;
         
        try (PreparedStatement statement = connection.prepareStatement(GET_NUMERO_VIUS)) {
            statement.setInt(1, id);
            
            try (ResultSet result = statement.executeQuery()) {
               
                
                if (result.next()) {
                   
                   visualizacoes = result.getInt("visualizacoes");
                   
                    
                } else {
                    throw new SQLException("Erro ao visualizar: postagem não encontrado.");
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

        return visualizacoes;
    }

    @Override
    public void setNumeroVizualizacoes(int id, int num) throws SQLException {
        String query;
            
                query = UPDATE_NUMERO_VISUALIZACOES;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            
            
            statement.setInt(1,num);
            statement.setInt(2,id);
            
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
    public List<Postagem> maisVisto() throws SQLException {
         List<Postagem> postList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(POSTAGENS_MAIS_VISTAS);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Postagem post = new Postagem();
                post.setPostagemId(result.getInt("postagemid"));
                post.setTitulo(result.getString("titulo"));
                post.setSubtitulo(result.getString("subtitulo"));
                post.setDescricao(result.getString("descricao"));               
                postList.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar Postagens mais vistas.");
        }

        return postList;
    }

    @Override
    public List<Postagem> postagemAreaUser(int id,int area, int buscarPor) throws SQLException {
        String order = null;
       
        List<Postagem> postList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(POSTAGENS_AREA_DO_USER)) {
            
            statement.setInt(1, id);
            statement.setInt(2, area);
            switch(buscarPor){
                case 1:
                    order = "createat";
                    break;
                case 2:
                    order = "visualizacoes";
                    break;
            }
            statement.setString(3, order);
            
           // System.out.println(statement);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Postagem post = new Postagem();
                    post.setPostagemId(result.getInt("postagemid"));
                    post.setTitulo(result.getString("titulo"));
                    post.setSubtitulo(result.getString("subtitulo"));
                    post.setDescricao(result.getString("descricao"));               
                    postList.add(post);
                }
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
        
        return postList;
    }

    @Override
    public int getPostId(String string) throws SQLException {
        int i=0;
        try (PreparedStatement statement = connection.prepareStatement(ID_POSTAGEM)){
            System.out.println(string);
            statement.setString(1, string);
            
            try(ResultSet result = statement.executeQuery()) {
                  if (result.next()) {
                   
                   i = result.getInt("postagemid");
                   
                     System.out.println(i);
                } else {
                    throw new SQLException("Erro ao visualizar: postagem não encontrado.");
                }
                 
                 
                
            } catch (SQLException ex) {
                Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

                throw new SQLException("Erro ao listar usuários.");
            }
        }catch(SQLException ex){
            
        }
          return i;      

    }
    
}
