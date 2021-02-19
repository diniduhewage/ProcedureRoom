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
import lk.gov.health.procedure.pojo.ProcedureTypePojo;

/**
 *
 * @author user
 */
@Named
@SessionScoped
public class ProcedureTypeCtrl implements Serializable {
    private ProcedureTypePojo selected = new ProcedureTypePojo();
    
    private ArrayList<ProcedureTypePojo> ProcTypeList;    

    public ArrayList<ProcedureTypePojo> getMedList() {
        return ProcTypeList;
    }

    public void setMedList(ArrayList<ProcedureTypePojo> medList) {
        this.ProcTypeList = medList;
    }

    public ProcedureTypePojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedureTypePojo selected) {
        this.selected = selected;
    }          
}
