/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.bean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lk.gov.health.procedure.pojo.ProcedureLogPojo;

/**
 *
 * @author user
 */
@Named("procedureLogCtrl")
@SessionScoped
public class ProcedureLogCtrl implements Serializable{
    private ProcedureLogPojo selected = new ProcedureLogPojo();
    private ArrayList<ProcedureLogPojo> items;

    public ProcedureLogPojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedureLogPojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcedureLogPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcedureLogPojo> items) {
        this.items = items;
    } 
    
    
}
