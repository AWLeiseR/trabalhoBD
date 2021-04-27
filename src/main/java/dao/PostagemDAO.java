/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.AuxReport;
import model.Postagem;

/**
 *
 * @author Alan
 * @param <Postagem>
 */
public interface PostagemDAO extends DAO<Postagem> {
    
    public List<Postagem> maisRecente() throws SQLException;
    public List<Postagem> maisVisto() throws SQLException;
    public List<Postagem> postagemAreaUser(int id,int area, int buscarPor) throws SQLException;
    public int getPostId(String titulo) throws SQLException;
    public List<AuxReport> getPostPerDay() throws SQLException;
    public int getTotalPost()  throws SQLException;
    public double getAveragePost() throws SQLException;
    public List<Postagem> getRelatedPost(int id) throws SQLException;
}
