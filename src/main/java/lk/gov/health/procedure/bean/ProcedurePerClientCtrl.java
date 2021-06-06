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
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.WebTarget;
import lk.gov.health.procedure.enums.ProcPerClientStates;
import lk.gov.health.procedure.pojo.InstitutePojo;
import lk.gov.health.procedure.pojo.ProcPerInstPojo;
import lk.gov.health.procedure.pojo.ProcedurePerClientPojo;
import lk.gov.health.procedure.util.ServiceConnector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("procedurePerClientCtrl")
@SessionScoped
public class ProcedurePerClientCtrl implements Serializable {

    private ProcedurePerClientPojo selected = new ProcedurePerClientPojo();
    private ProcPerInstPojo medProcPerInst = new ProcPerInstPojo();
    private InstitutePojo institute = new InstitutePojo();
    private ArrayList<ProcedurePerClientPojo> items;
    private ArrayList<ProcPerInstPojo> procList;
    private String outputComment;

    private static String SERVICE_URI;
    private WebTarget webTarget;
    private javax.ws.rs.client.Client client;

    public ProcedurePerClientCtrl() {
        SERVICE_URI = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("SERVICE_APP_URL") + "procedureperclient";
        client = javax.ws.rs.client.ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
        webTarget = client.target(SERVICE_URI);
    }

    public String toAddProcedure() {
        this.getProcedures();
        return "/pages/medicalprocedures";
    }

    public String toProcedureLog() {
        selected = new ProcedurePerClientPojo();
//        this.getProcedureLog();
        return "/pages/procedure_log";
    }

    public void prepareNew() {
        selected = new ProcedurePerClientPojo();
    }

    private void getProcedures() {
        try {
            Client client = Client.create();
            WebResource webResource1 = client.resource(SERVICE_URI);
            ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
            String outpt = cr.getEntity(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(ProcPerInstCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getProceduresPerInstitution() {
        try {
            items = null;
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("/filer_list/{0}", new Object[]{selected.getInstituteId().getId().toString()}));
            String outpt = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(ProcPerInstCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ProcPerInstPojo> fetchProcedures(String qryVal) {
        String url_ = SERVICE_URI+"/filer_list/" + institute.getCode() + "/" + qryVal;

        ServiceConnector sc_ = new ServiceConnector();
        return medProcPerInst.getObjectList(sc_.GetRequestList(url_));
    }    

    public void saveClientProcedure() {
        Client client = Client.create();
        if (selected.getId() == null) {
            JSONObject jo = selected.getJsonObject();
            WebResource webResource1 = client.resource(SERVICE_URI+"/register_procedure");
            ClientResponse response = webResource1.type("application/json").post(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure added successfully");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to add new record");
            }

        } else {
            JSONObject jo = selected.getUpdJsonObject();
            jo.put("id", selected.getId());
            WebResource webResource2 = client.resource(SERVICE_URI+"/update_procedure/" + selected.getId()+"/"+selected.getStatus());
            ClientResponse response = webResource2.type("application/json").put(ClientResponse.class, jo.toString());
            if (response.getStatus() == 200 || response.getStatus() == 204) {
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure Updated Successfully");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
            }
        }
    }

    public void deleteClientProcedure() {
        Client client = Client.create();
        WebResource r_ = client.resource(SERVICE_URI + selected.getId());
        ClientResponse response = r_.type("application/json").delete(ClientResponse.class);
        if (response.getStatus() == 200 || response.getStatus() == 204) {
            addMessage(FacesMessage.SEVERITY_INFO, "Success", "Procedure deleted successfully");
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error ocured..! Unable to update record");
        }
    }

    public void addMessage(Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public ProcPerClientStates[] getProcClientStatus() {
        return ProcPerClientStates.values();
    }

    public ProcedurePerClientPojo getSelected() {
        return selected;
    }

    public void setSelected(ProcedurePerClientPojo selected) {
        this.selected = selected;
    }

    public ArrayList<ProcedurePerClientPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProcedurePerClientPojo> items) {
        this.items = items;
    }

    public ArrayList<ProcPerInstPojo> getProcList() {
        return procList;
    }

    public void setProcList(ArrayList<ProcPerInstPojo> procList) {
        this.procList = procList;
    }

    public ProcPerInstPojo getmedProcPerInst() {
        return medProcPerInst;
    }

    public void setmedProcPerInst(ProcPerInstPojo medProcPerInst) {
        this.medProcPerInst = medProcPerInst;
    }

    public InstitutePojo getInstitute() {
        return institute;
    }

    public void setInstitute(InstitutePojo institute) {
        this.institute = institute;
    }

    public String getOutputComment() {
        return outputComment;
    }

    public void setOutputComment(String outputComment) {
        this.outputComment = outputComment;
    }

    private SSLContext getSSLContext() {
        // for alternative implementation checkout org.glassfish.jersey.SslConfigurator
        javax.net.ssl.TrustManager x509 = new javax.net.ssl.X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {
                return;
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {
                return;
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("SSL");
            ctx.init(null, new javax.net.ssl.TrustManager[]{x509}, null);
        } catch (java.security.GeneralSecurityException ex) {
        }
        return ctx;
    }
}
