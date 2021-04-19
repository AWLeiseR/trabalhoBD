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
public class AuxReport {
    private int intField;
    private Date dateField;
    
    /**
     * @return the intField
     */
    public int getIntField() {
        return intField;
    }

    /**
     * @param intField the intField to set
     */
    public void setIntField(int intField) {
        this.intField = intField;
    }

    /**
     * @return the dateField
     */
    public Date getDateField() {
        return dateField;
    }

    /**
     * @param dateField the dateField to set
     */
    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }
      
}   
