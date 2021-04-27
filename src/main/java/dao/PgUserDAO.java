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
import model.AuxReport;

/**
 *
 * @author dskaster
 * 
 */
public class PgUserDAO implements UserDAO {

    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO revista.users(nome, sobrenome, email, senha, funcao, createat) " +
                                "VALUES(?,?,?, md5(?),?, ?);";

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
                                "WHERE email = ? AND senha = md5(?);";
    
    private static final String UPDATE_FUNCAO = 
                                "UPDATE revista.users "+
                                "SET funcao = ? WHERE userid = ? ;";

    private static final String AUTHENTICATE_QUERY =
                                "SELECT userid, nome " +
                                "FROM revista.users " +
                                "WHERE email = ? AND senha = md5(?);";
    
    private static final String GET_FUNCAO_ID =
                                "SELECT funcao " +
                                "FROM revista.users " +
                                "WHERE userId=?;";
    
     private static final String GET_EMAIL_ID =
                                "SELECT userid " +
                                "FROM revista.users " +
                                "WHERE email=?;";
     
     private static final String GET_USER_PER_DAY =
                                "select (case when table2.num is not null " +
                                "then table2.num else 0 end) as qtd, table1.dia from (select (date_trunc('day',(current_date-30)::date)::date)+(i*1) as dia " +
                                "from generate_Series(0,30) i) as table1 full join " +
                                "(select count(*) as num , createat as dia from revista.users group by createat) as table2 " +
                                "on table1.dia = table2.dia";
     
     private static final String GET_TOTAL_USER =
                                "select count(*)as total from revista.users";
     
     private static final String GET_AVERAGE_USER =
                                "select avg(table3.qtd) as average from (select (case when table2.num is not null " +
                                "then table2.num else 0 end) as qtd, table1.dia from (select (date_trunc('day',(current_date-30)::date)::date)+(i*1) as dia " +
                                "from generate_Series(0,30) i) as table1 full join " +
                                "(select count(*) as num , createat as dia from revista.users group by createat) as table2 " +
                                "on table1.dia = table2.dia) as table3";
     
     private static final String GET_SCORE_USER=
                                "select * from revista.userScore();";
     
    public PgUserDAO(Connection connection) {
        this.connection = connection;
    }
                    
    @Override
    public void create(User t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            
            statement.setString(1, t.getNome());
            statement.setString(2, t.getSobrenome());
            statement.setString(3, t.getEmail());
            statement.setString(4, t.getSenha());
            statement.setString(5, "membro");
            statement.setDate(6, t.getCreateAt());

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
                    user.setUserId(result.getInt("userid"));
                    user.setNome(result.getString("nome"));
                   
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
    public String getFuncao(User user) throws SQLException {
       String funcao;

        try (PreparedStatement statement = connection.prepareStatement(GET_FUNCAO)) {
             statement.setString(1, user.getEmail());
            statement.setString(2, user.getSenha());
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

    @Override
    public String getFuncao(int id) throws SQLException {
        String funcao;

        try (PreparedStatement statement = connection.prepareStatement(GET_FUNCAO_ID)) {
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
    public int getId(String string) throws SQLException {
        int id;

        try (PreparedStatement statement = connection.prepareStatement(GET_EMAIL_ID)) {
             statement.setString(1, string);
            
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    id=result.getInt("userid");
                    
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

        return id; 
    }

    @Override
    public List<AuxReport> getUserPerDay() throws SQLException {
            List<AuxReport> report = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_PER_DAY);
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
    public int getTotalUser() throws SQLException {
        int total;
        try (PreparedStatement statement = connection.prepareStatement(GET_TOTAL_USER);
             ResultSet result = statement.executeQuery()) {
            if (result.next()) {

                     total = result.getInt("total");

            }else{
                 throw new SQLException("Erro ao visualizar: total de users nao encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao pegar total users.");
        }
        return total;
    }

    @Override
    public double getAverageUser() throws SQLException {
         double average;
        try (PreparedStatement statement = connection.prepareStatement(GET_AVERAGE_USER);
             ResultSet result = statement.executeQuery()) {
           if (result.next()) {
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
    public List<AuxReport> getUserScore() throws SQLException {
        List<AuxReport> report = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_SCORE_USER);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                AuxReport userRepo = new AuxReport();
               
                     userRepo.setIntField(result.getInt("score"));
                    userRepo.setIntFieldTwo(result.getInt("iduser"));
                    report.add(userRepo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar relatorio.");
        }
        return report;
    }
    

}
