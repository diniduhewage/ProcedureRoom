/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.WebTarget;
import lk.gov.health.procedure.entity.Item;
import lk.gov.health.procedure.entity.UserPrivilege;
import lk.gov.health.procedure.entity.WebUser;
import lk.gov.health.procedure.facade.UserPrivilegeFacade;
import lk.gov.health.procedure.facade.WebUserFacade;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 *
 * @author user
 */
@Named("webUserCtrl")
@SessionScoped
public class WebUserCtrl implements Serializable {    

    @EJB
    private WebUserFacade ejbFacade;
    @EJB
    private UserPrivilegeFacade userPrivilegeFacade;

    private String userName;
    private String personName;
    private String procRoomList;
    private String userId;
    private String apiKey;
    private String userRole;
    private String userRoleLabel;
    private String insId;
    private String insName;
    
    private WebTarget webTarget;
    private javax.ws.rs.client.Client client;
    private static String BASE_URI;
    
    public WebUserCtrl(){
        BASE_URI = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("BASE_APP_URL");
        client = javax.ws.rs.client.ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
        webTarget = client.target(BASE_URI);
    }


    public boolean IsSystemAdmin() {
        return userRole != null && this.userRole.equals("System_Administrator");
    }

    public boolean IsInstituteAdmin() {
        return userRole != null && this.userRole.matches("Institution_Administrator|System_Administrator");
    }

    public boolean IsUser() {
        return userRole != null && this.userRole.matches("Nurse|Institution_Administrator|System_Administrator");
    }

    public String imageLocation() {
        return "resources/image/hims_logo.png";
    }    

    public String logOut() {
        return "/index";
    }

    public boolean matchPassword(String planePassword, String encryptedPassword) {
        BasicPasswordEncryptor en = new BasicPasswordEncryptor();
        return en.checkPassword(planePassword, encryptedPassword);
    }

    public List<UserPrivilege> userPrivilegeList(WebUser u) {
        return userPrivilegeList(u, null);
    }

    public List<UserPrivilege> userPrivilegeList(Item i) {
        return userPrivilegeList(null, i);
    }

    public List<UserPrivilege> userPrivilegeList(WebUser u, Item i) {
        String j = "select p from UserPrivilege p "
                + " where p.retired=false ";
        Map m = new HashMap();
        if (u != null) {
            j += " and p.webUser=:u ";
            m.put("u", u);
        }
        if (i != null) {
            j += " and p.item=:i ";
            m.put("i", i);
        }
        return getUserPrivilegeFacade().findByJpql(j, m);
    }

    public String getWebUserRoleLabel() {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("/get_role_name/{0}",new Object[]{this.userRole}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }
    
    public String getInsName() {   
        System.out.println("1111111111111111 -->"+this.insId);
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("/get_institution_name/{0}",new Object[]{this.insId}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }
    
    public String getPersonName() {        
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("/get_user_name/{0}",new Object[]{this.userId}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    public WebUserFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(WebUserFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserPrivilegeFacade getUserPrivilegeFacade() {
        return userPrivilegeFacade;
    }

    public void setUserPrivilegeFacade(UserPrivilegeFacade userPrivilegeFacade) {
        this.userPrivilegeFacade = userPrivilegeFacade;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    public String getUserRoleLabel() {
        return userRoleLabel;
    }

    public void setUserRoleLabel(String userRoleLabel) {
        this.userRoleLabel = userRoleLabel;
    }
    
    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getProcRoomList() {
        return procRoomList;
    }

    public void setProcRoomList(String procRoomList) {
        this.procRoomList = procRoomList;
    }    

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
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
