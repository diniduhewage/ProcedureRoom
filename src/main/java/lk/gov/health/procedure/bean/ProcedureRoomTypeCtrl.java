/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.bean;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lk.gov.health.procedure.pojo.ProcedureRoomTypePojo;
import lk.gov.health.procedure.util.ServiceConnector;
import org.json.simple.JSONObject;

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

    public String toProcRoomTypes() {
        selected = new ProcedureRoomTypePojo();
        getProcRoomTypes();
        return "/pages/room_types";
    }

    public void saveRoomType() {
        JSONObject jo = selected.getJsonObject();

        Client client = Client.create();

        if (selected.getId() == null) {
            jo.put("id", 123654);
            WebResource webResource1 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureroomtype");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 202) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Added Successfully");
            } else {
                response.bufferEntity();
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", response.getEntity(String.class));
            }

        } else {
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureroomtype/" + selected.getId());
            ClientResponse response = webResource2.type("application/json").put(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Room Type Updated Successfully");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", response.getEntity(String.class));
            }
        }
    }

    public void deleteRoomType() {
        Client client = Client.create();
        WebResource webResource2 = client.resource("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureroomtype/" + selected.getId());
        webResource2.delete();
    }

    public void addMessage(Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(sev, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
