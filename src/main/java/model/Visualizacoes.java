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
public class Visualizacoes {

    private int idUser;
    private int idPostagem;
    private Date visualizacaoData;
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
     * @return the visualizacaoData
     */
    public Date getVisualizacaoData() {
        return visualizacaoData;
    }

    /**
     * @param visualizacaoData the visualizacaoData to set
     */
    public void setVisualizacaoData(Date visualizacaoData) {
        this.visualizacaoData = visualizacaoData;
    }
    
}
