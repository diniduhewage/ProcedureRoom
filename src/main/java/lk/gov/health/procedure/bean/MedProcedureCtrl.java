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
import lk.gov.health.procedure.pojo.MedProcedurePojo;

/**
 *
 * @author user
 */
@Named
@SessionScoped
public class MedProcedureCtrl implements Serializable{
    private MedProcedurePojo med_pojo_ = new MedProcedurePojo();
    
    private ArrayList<MedProcedurePojo> medList;
         

    public MedProcedurePojo getMed_pojo_() {
        return med_pojo_;
    }

    public void setMed_pojo_(MedProcedurePojo med_pojo_) {
        this.med_pojo_ = med_pojo_;
    }

    public ArrayList<MedProcedurePojo> getMedList() {
        return medList;
    }

    public void setMedList(ArrayList<MedProcedurePojo> medList) {
        this.medList = medList;
    }
    
    
    
    
    
            
}
