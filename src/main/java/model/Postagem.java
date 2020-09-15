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
public class Postagem {
    private int postagemId;
    private String titulo;
    private String subtitulo;
    private String descricao;
    private String conteudo;
    private int visualizacoes;
    private Date createAt;
    private Date alteradoAt;

    /**
     * @return the postagemId
     */
    public int getPostagemId() {
        return postagemId;
    }

    /**
     * @param postagemId the postagemId to set
     */
    public void setPostagemId(int postagemId) {
        this.postagemId = postagemId;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the subtitulo
     */
    public String getSubtitulo() {
        return subtitulo;
    }

    /**
     * @param subtitulo the subtitulo to set
     */
    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the conteudo
     */
    public String getConteudo() {
        return conteudo;
    }

    /**
     * @param conteudo the conteudo to set
     */
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    /**
     * @return the visualizacoes
     */
    public int getVisualizacoes() {
        return visualizacoes;
    }

    /**
     * @param visualizacoes the visualizacoes to set
     */
    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    /**
     * @return the createAt
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * @param createAt the createAt to set
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * @return the alteradoAt
     */
    public Date getAlteradoAt() {
        return alteradoAt;
    }

    /**
     * @param alteradoAt the alteradoAt to set
     */
    public void setAlteradoAt(Date alteradoAt) {
        this.alteradoAt = alteradoAt;
    }
    
}
