/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.Highlight;

/**
 *
 * @author Alan
 */
public class PgHighlightDAO implements DAO<Highlight> {
    
    private final Connection connection;
    
    public PgHighlightDAO(Connection connection) {
        this.connection = connection;
    }

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
                                "SELECT userid, nome " +
                                "FROM revista.users " +
                                "ORDER BY userid;";

   /* private static final String AUTHENTICATE_QUERY =
                                "SELECT id, pnome, snome " +
                                "FROM j2ee.user " +
                                "WHERE email = ? AND senha = md5(?);";*/

    @Override
    public void create(Highlight t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Highlight read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Highlight t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Highlight> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
