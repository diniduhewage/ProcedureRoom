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
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.WebTarget;
import lk.gov.health.procedure.facade.util.JsfUtil;
import lk.gov.health.procedure.pojo.InstitutePojo;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
@Named("procedureRoomCtrl")
@SessionScoped
public class ProcedureRoomCtrl implements Serializable {

    @Inject
    private ProcGroupInstituteCtrl procGroupInstituteCtrl;
    @Inject
    private ProcPerInstCtrl procPerInstCtrl;
    @Inject
    private ProcedurePerClientCtrl procPerClientCtrl;

    private InstitutePojo selected = new InstitutePojo();
    private ArrayList<InstitutePojo> items;

    private static String SERVICE_URI;
    private WebTarget webTarget;
    private javax.ws.rs.client.Client client;

    public ProcedureRoomCtrl() {
        SERVICE_URI = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("SERVICE_APP_URL")+"institute";
        client = javax.ws.rs.client.ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
        webTarget = client.target(SERVICE_URI);
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

    public String toProcedureRoom(String userRole, String instituteCode) {
        selected = new InstitutePojo();

        this.getProcedureRoomsPerInstitute(userRole, instituteCode);
        return "/pages/procedure_room";
    }

    public String toProcedurePerInstitute() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to Manage");
            return "";
        }
        procPerInstCtrl.setInstitute(selected);
        procPerInstCtrl.getProceduresPerInstitution(selected.getCode());
        return "/pages/procedure_per_institute";
    }

    public String toClientProcedurePerInstitute() {
        procPerClientCtrl.getSelected().setInstituteId(selected);
        procPerClientCtrl.setInstitute(selected);
        procPerClientCtrl.getProceduresPerInstitution();
        return "/pages/medicalprocedures";
    }

    public void addMessage(FacesMessage.Severity sev, String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void getProcedureRooms() {
        WebTarget resource = webTarget;
        try {
            resource = resource.path(java.text.MessageFormat.format("get_procedure_rooms/{0}", new Object[]{"NO_FILTER"}));
            String outpt = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAllocatedGroups() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to Manage");
            return "";
        }
        procGroupInstituteCtrl.setInstitute(selected);
        procGroupInstituteCtrl.fetchGroupsPerInstitute();
        return "/pages/procedure_group_institute";
    }

    public void getProcedureRoomsPerInstitute(String userRole, String insCode) {
        WebTarget resource = webTarget;
        try {
            if (userRole.equals("System_Administrator")) {
                resource = resource.path(java.text.MessageFormat.format("get_procedure_rooms/{0}", new Object[]{"NO_FILTER"}));
            } else {
                resource = resource.path(java.text.MessageFormat.format("get_procedure_rooms/{0}", new Object[]{insCode}));
            }
            String outpt = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
            
            items = selected.getObjectList((JSONArray) new JSONParser().parse(outpt));
        } catch (ParseException ex) {
            Logger.getLogger(MedProcedureCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ProcGroupInstituteCtrl getProcGroupInstituteCtrl() {
        return procGroupInstituteCtrl;
    }

    public void setProcGroupInstituteCtrl(ProcGroupInstituteCtrl procGroupInstituteCtrl) {
        this.procGroupInstituteCtrl = procGroupInstituteCtrl;
    }

    public ProcPerInstCtrl getProcPerInstCtrl() {
        return procPerInstCtrl;
    }

    public void setProcPerInstCtrl(ProcPerInstCtrl procPerInstCtrl) {
        this.procPerInstCtrl = procPerInstCtrl;
    }

    public ProcedurePerClientCtrl getProcPerClientCtrl() {
        return procPerClientCtrl;
    }

    public void setProcPerClientCtrl(ProcedurePerClientCtrl procPerClientCtrl) {
        this.procPerClientCtrl = procPerClientCtrl;
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
