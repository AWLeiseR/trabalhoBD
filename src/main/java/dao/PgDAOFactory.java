/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;

/**
 *
 * @author dskaster
 */
public class PgDAOFactory extends DAOFactory {

    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public DAO getUserDAO() {
        return new PgUserDAO(this.connection);
    }

    @Override
    public DAO getAreaDAO() {
        return new PgAreaDAO(this.connection);
    }

    @Override
    public DAO getPostagemDAO() {
        return new PgPostagemDAO(this.connection);
    }

    @Override
    public DAO getUserAreasDAO() {
        return new PgUserAreasDAO(this.connection);
    }

    @Override
    public DAO getPostagemAreasDAO() {
        return new PgPostagemAreasDAO(this.connection);
    }

    @Override
    public DAO getHighlightDAO() {
        return new PgHighlightDAO(this.connection);    }
    
    
}
