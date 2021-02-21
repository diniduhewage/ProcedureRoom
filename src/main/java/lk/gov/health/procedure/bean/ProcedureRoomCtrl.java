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
import lk.gov.health.procedure.pojo.ProcedureRoomPojo;

/**
 *
 * @author user
 */
@Named
@SessionScoped
public class ProcedureRoomCtrl implements Serializable  {
    private ProcedureRoomPojo selected = new ProcedureRoomPojo();    
    private ArrayList<ProcedureRoomPojo> items;

    public ProcedureRoomPojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedureRoomPojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcedureRoomPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcedureRoomPojo> items) {
        this.items = items;
    }
    
     public String toMedProcedure() {
        selected = new ProcedureRoomPojo();
        return "/pages/procedure_room";
    }
    
     
    
}
