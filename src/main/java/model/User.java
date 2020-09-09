/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Alan
 */
public class User {
  private Integer userId;
  private String pNome;
  private String sNome;
  private String email;
  private String senha;
  private String funcao;

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the pNome
     */
    public String getpNome() {
        return pNome;
    }

    /**
     * @param pNome the pNome to set
     */
    public void setpNome(String pNome) {
        this.pNome = pNome;
    }

    /**
     * @return the sNome
     */
    public String getsNome() {
        return sNome;
    }

    /**
     * @param sNome the sNome to set
     */
    public void setsNome(String sNome) {
        this.sNome = sNome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the funcao
     */
    public String getFuncao() {
        return funcao;
    }

    /**
     * @param funcao the funcao to set
     */
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
  
  
}
