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
import model.User;

/**
 *
 * @author dskaster
 * 
 */
public class PgUserDAO implements UserDAO {

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

    private static final String UPDATE_WITH_PASSWORD_QUERY =
                                "UPDATE revista.users " +
                                "SET nome= ?, sobrenome= ?, email= ?, funcao=? , senha = md5(?) " +
                                "WHERE userid = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM revista.users " +
                                "WHERE userid = ?;";

    private static final String ALL_QUERY =
                                "SELECT userid, nome, funcao " +
                                "FROM revista.users " +
                                "ORDER BY userid;";
    
    private static final String GET_FUNCAO =
                                "SELECT funcao " +
                                "FROM revista.users " +
                                "WHERE userId=?;";
    
    private static final String UPDATE_FUNCAO = 
                                "UPDATE revista.users "+
                                "SET funcao = ? WHERE userid = ? ;";

    private static final String AUTHENTICATE_QUERY =
                                "SELECT id, pnome, snome " +
                                "FROM j2ee.user " +
                                "WHERE email = ? AND senha = md5(?);";

    public PgUserDAO(Connection connection) {
        this.connection = connection;
    }
                    
    @Override
    public void create(User t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            
            statement.setInt(1, t.getUserId());
            statement.setString(2, t.getNome());
            statement.setString(3, t.getSobrenome());
            statement.setString(4, t.getEmail());
            statement.setString(5, t.getSenha());
            statement.setString(6, "membro");

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao inserir usuário: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir usuário.");
            }
        }
    }

    @Override
    public User read(Integer id) throws SQLException {
        User user = new User();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    user.setUserId(id);
                    user.setNome(result.getString("nome"));
                    user.setSobrenome(result.getString("sobrenome"));
                    user.setEmail(result.getString("email"));
                    user.setFuncao(result.getString("funcao"));
                } else {
                    throw new SQLException("Erro ao visualizar: usuário não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar usuário.");
            }
        }

        return user;
    }

    @Override
    public void update(User t) throws SQLException {
        String query;

        if (t.getSenha() == null) {
            
                query = UPDATE_QUERY;
 
        } else {
           
                query = UPDATE_WITH_PASSWORD_QUERY;
           
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, t.getNome());
            statement.setString(2, t.getSobrenome());
            statement.setString(3, t.getEmail());
            statement.setString(4, t.getFuncao());

            if (t.getSenha() == null) {
               
                    statement.setInt(5, t.getUserId());
              
            } else {

                    statement.setString(5, t.getSenha());
                    statement.setInt(6, t.getUserId());
               
            }

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: usuário não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao editar usuário: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar usuário.");
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir usuário.");
            }
        }
    }

    @Override
    public List<User> all() throws SQLException {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                User user = new User();
                user.setUserId(result.getInt("userId"));
               user.setNome(result.getString("nome"));
               user.setFuncao(result.getString("funcao"));

                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return userList;
    }

    @Override
    public void authenticate(User user) throws SQLException, SecurityException {
        try (PreparedStatement statement = connection.prepareStatement(AUTHENTICATE_QUERY)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getSenha());

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    user.setUserId(result.getInt("id"));
                    user.setSobrenome(result.getString("nome"));
                    user.setNome(result.getString("nascimento"));
                } else {
                    throw new SecurityException("Login ou senha incorretos.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao autenticar usuário.");
        }
    }

    @Override
    public User getByLogin(String login) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFuncao(int id) throws SQLException {
       String funcao;

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    funcao=result.getString("funcao");
                    
                } else {
                    throw new SQLException("Erro ao visualizar: usuário não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar usuário.");
            }
        }

        return funcao; 
    }

    @Override
    public void updateFuncao(int id, String funcao) throws SQLException {
        String query;
        
                query = UPDATE_FUNCAO;
                
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, funcao);
            statement.setInt(2, id);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: usuário não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao editar usuário: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar usuário.");
            }
        }
    }

}
