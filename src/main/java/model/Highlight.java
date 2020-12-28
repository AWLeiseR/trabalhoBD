/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Alan
 */
public class Highlight {

    private int idUser;
    private int idPostagem;
    private Date highlightDate;
    /**
     * @return the idUser
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the idPostagem
     */
    public int getIdPostagem() {
        return idPostagem;
    }

    /**
     * @param idPostagem the idPostagem to set
     */
    public void setIdPostagem(int idPostagem) {
        this.idPostagem = idPostagem;
    }
    /**
     * @return the highlightDate
     */
    public Date getHighlightDate() {
        return highlightDate;
    }

    /**
     * @param highlightDate the highlightDate to set
     */
    public void setHighlightDate(Date highlightDate) {
        this.highlightDate = highlightDate;
    }
}
