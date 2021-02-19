/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.bean;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.TableColumn.CellEditEvent;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lk.gov.health.procedure.pojo.ProcedureRoomTypePojo;
import lk.gov.health.procedure.util.ServiceConnector;

/**
 *
 * @author user
 */
@Named("ProcedureRoomTypeCtrl")
@SessionScoped
public class ProcedureRoomTypeCtrl implements Serializable {

    public ProcedureRoomTypeCtrl() {

    }
    private ProcedureRoomTypePojo selected = new ProcedureRoomTypePojo();

    private ArrayList<ProcedureRoomTypePojo> items = new ArrayList<>();

    public ProcedureRoomTypePojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedureRoomTypePojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcedureRoomTypePojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcedureRoomTypePojo> items) {
        this.items = items;
    }

    public void getProcRoomTypes() {
        items.clear();
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureroomtype";

        ServiceConnector sc_ = new ServiceConnector();
        items = selected.getObjectList(sc_.GetRequestList(url_));       
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
