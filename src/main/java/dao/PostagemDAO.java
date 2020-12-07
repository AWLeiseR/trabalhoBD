/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Postagem;

/**
 *
 * @author Alan
 * @param <Postagem>
 */
public interface PostagemDAO<Postagem> extends DAO<Postagem> {
    @Override
    public void create(Postagem t) throws SQLException;
    @Override
    public Postagem read(Integer id) throws SQLException;
    @Override
    public void update(Postagem t) throws SQLException;
    @Override
    public void delete(Integer id) throws SQLException;

    @Override
    public List<Postagem> all() throws SQLException;   
    
    public List<Postagem> maisRecente() throws SQLException;

}
