/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.AreasDeInteresse;
import model.UserAreas;

/**
 *
 * @author Alan
 */
public interface UserAreasDAO  extends DAO<UserAreas> {
    public List<UserAreas> todasAreasUser(Integer id) throws SQLException;
}
