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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import lk.gov.health.procedure.pojo.InstitutePojo;
import lk.gov.health.procedure.util.ServiceConnector;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("institutionListCtrl")
@SessionScoped
public class InstitutionListCtrl implements Serializable {

    private InstitutePojo selected = new InstitutePojo();
    private ArrayList<InstitutePojo> items = new ArrayList<>();

    String baseUrl = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice";
    String mainAppUrl = "http://localhost:8080/chims/data?name=";

    public String toInstitutionList() {
        selected = new InstitutePojo();
        this.getInstitutes();
        return "/pages/institution_list";
    }

    public void getInstitutes() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".institute/data?name=get_procedure_room_list");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(DrugFrequencyCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getProcedureRooms() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(baseUrl + ".institute/get_procedure_rooms/NO_FILTER");
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(DrugFrequencyCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void syncInstitutes() {
        Client client = Client.create();
        WebResource webResource1 = client.resource(baseUrl + ".institute/sync_institutes");
        ClientResponse response = webResource1.type("application/json").post(ClientResponse.class);
        if (response.getStatus() == 200 || response.getStatus() == 204) {
            addMessage(FacesMessage.SEVERITY_INFO, "Success", "Institutes were synced Successfully");
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to sync institutes");
        }
    }

    public ArrayList<InstitutePojo> fetchInstitutes(String qryVal) {
        String url_ = "http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.institute/filer_list/" + qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return selected.getObjectList(sc_.GetRequestList(url_));
    }

    public void addMessage(FacesMessage.Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }    

    static {
        HttpsURLConnection.setDefaultHostnameVerifier((String hostname, SSLSession session) -> hostname.equals("192.168.202.39"));
    }

    public InstitutePojo getSelected() {
        return selected;
    }

    public void setSelected(InstitutePojo selected) {
        this.selected = selected;
    }

    public ArrayList<InstitutePojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<InstitutePojo> items) {
        this.items = items;
    }

}
