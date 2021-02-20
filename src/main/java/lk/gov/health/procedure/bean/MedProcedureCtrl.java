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
import lk.gov.health.procedure.pojo.ProcedureRoomTypePojo;
import lk.gov.health.procedure.pojo.ProcedureTypePojo;
import lk.gov.health.procedure.util.ServiceConnector;

/**
 *
 * @author user
 */
@Named("MedProcedureCtrl")
@SessionScoped
public class MedProcedureCtrl implements Serializable {

    private MedProcedurePojo selected = new MedProcedurePojo();
    private ArrayList<ProcedureTypePojo> procTypes;
    ProcedureTypePojo procType = new ProcedureTypePojo();
    ProcedureRoomTypePojo roomType = new ProcedureRoomTypePojo();

    private ArrayList<MedProcedurePojo> items;

    public MedProcedurePojo getSelected() {
        return selected;
    }

    public void setSelected(MedProcedurePojo selected) {
        this.selected = selected;
    }

    public ArrayList<MedProcedurePojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<MedProcedurePojo> items) {
        this.items = items;
    }

    public ArrayList<ProcedureTypePojo> getProcTypes() {
        return procTypes;
    }

    public void setProcTypes(ArrayList<ProcedureTypePojo> procTypes) {
        this.procTypes = procTypes;
    }

    public String toMedProcedure() {
        selected = new MedProcedurePojo();
        return "/pages/procedure";
    }

    public ArrayList<ProcedureTypePojo> fetchProcTypes() {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.proceduretype";

        ServiceConnector sc_ = new ServiceConnector();
        return procType.getObjectList(sc_.GetRequestList(url_));
    }
    
    public ArrayList<ProcedureRoomTypePojo> fetchRoomTypes() {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureroomtype";

        ServiceConnector sc_ = new ServiceConnector();
        return roomType.getObjectList(sc_.GetRequestList(url_));
    }

}
