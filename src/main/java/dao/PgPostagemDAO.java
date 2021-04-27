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
import model.AuxReport;
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
    
    private static final String GET_POST_PER_DAY =
                                "select (case when table2.num is not null " +
                                "then table2.num else 0 end) as qtd, table1.dia from (select (date_trunc('day',(current_date-30)::date)::date)+(i*1) as dia " +
                                "from generate_Series(0,30) i) as table1 full join " +
                                "(select count(*) as num , createat as dia from revista.postagem group by createat) as table2 " +
                                "on table1.dia = table2.dia";
    
    private static final String GET_TOTAL_POST =
                                "select count(*)as total from revista.postagem";
    
    private static final String GET_AVERAGE_POST =
                                "select avg(table3.qtd) as average from (select (case when table2.num is not null " +
                                "then table2.num else 0 end) as qtd, table1.dia from (select (date_trunc('day',(current_date-30)::date)::date)+(i*1) as dia " +
                                "from generate_Series(0,30) i) as table1 full join " +
                                "(select count(*) as num , createat as dia from revista.postagem group by createat) as table2 " +
                                "on table1.dia = table2.dia) as table3";
    
    private static final String GET_RELATED_POST =
                                "select table2.idpostagem as postagemid,postagem.titulo, postagem.subtitulo from (select visualizacoes.idpostagem from revista.visualizacoes\n" +
                                "join (select iduser, idpostagem from revista.visualizacoes \n" +
                                "where idpostagem = ? group by iduser, visualizacoes.idpostagem) as table1\n" +
                                "on visualizacoes.iduser = table1.iduser and visualizacoes.idpostagem != ?\n" +
                                "group by visualizacoes.idpostagem ) as table2 join revista.postagemareas on table2.idpostagem = postagemareas.idpostagem \n" +
                                "join revista.postagem on table2.idpostagem = postagem.postagemid LIMIT  3 ";
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

    @Override
    public List<AuxReport> getPostPerDay() throws SQLException {
        List<AuxReport> report = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_POST_PER_DAY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                AuxReport userRepo = new AuxReport();
                
                 if(result.getDate("dia") != null){
                     userRepo.setIntField(result.getInt("qtd"));
                
                    userRepo.setDateField(result.getDate("dia"));
                    report.add(userRepo);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar relatorio.");
        }
        return report;
    }

    @Override
    public int getTotalPost() throws SQLException {
        int total;
        try (PreparedStatement statement = connection.prepareStatement(GET_TOTAL_POST);
             ResultSet result = statement.executeQuery()) {
           if (result.next()) {
                 total = result.getInt("total");
                    
            } else {
                throw new SQLException("Erro ao visualizar: total de views nao encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao pegar total de views.");
        }
        return total;
    }

    @Override
    public double getAveragePost() throws SQLException {
        double average;
        try (PreparedStatement statement = connection.prepareStatement(GET_AVERAGE_POST);
             ResultSet result = statement.executeQuery()) {
           if (result.next()) {
               System.out.println(result.getDouble("average"));
                 average = result.getDouble("average");
                    
            } else {
                throw new SQLException("Erro ao visualizar: total de views nao encontrado.");
            } 
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao pegar total de views.");
        }
        return average;
    }

    @Override
    public List<Postagem> getRelatedPost(int id) throws SQLException {
        List<Postagem> postList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_RELATED_POST)){
            statement.setInt(1, id);
            statement.setInt(2, id);
            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Postagem post = new Postagem();
                    
                    post.setPostagemId(Integer.valueOf(result.getString("postagemid")));
                    
                    post.setTitulo(result.getString("titulo"));
                    post.setSubtitulo(result.getString("subtitulo"));              
                    postList.add(post);
                }
            }catch (SQLException ex) {
                Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

                throw new SQLException("Erro ao listar postagens.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PgPostagemDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar Postagens.");
        }

        return postList;
    }
    
}
