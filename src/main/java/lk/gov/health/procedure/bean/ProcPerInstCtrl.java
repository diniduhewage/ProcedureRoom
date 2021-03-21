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
import lk.gov.health.procedure.pojo.ProcPerInstPojo;

/**
 *
 * @author user
 */
@Named("procPerInstCtrl")
@SessionScoped
public class ProcPerInstCtrl implements Serializable{
    
    public ProcPerInstCtrl() {
    }
    private ProcPerInstPojo selected = new ProcPerInstPojo();
    private ArrayList<ProcPerInstPojo> items = new ArrayList<>();
    
    public String toAddProcedure() {
        selected = new ProcPerInstPojo();
//        this.getProcedures();
        return "/pages/procedure_per_institute";
    }

    public ProcPerInstPojo getSelected() {
        return selected;
    }

    public void setSelected(ProcPerInstPojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcPerInstPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcPerInstPojo> items) {
        this.items = items;
    }      
}
