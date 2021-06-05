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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lk.gov.health.procedure.entity.Item;
import lk.gov.health.procedure.entity.UserPrivilege;
import lk.gov.health.procedure.entity.WebUser;
import lk.gov.health.procedure.facade.UserPrivilegeFacade;
import lk.gov.health.procedure.facade.WebUserFacade;
import lk.gov.health.procedure.facade.util.JsfUtil;
import lk.gov.health.procedure.util.ServiceConnector;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.json.simple.JSONObject;

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

    String mainAppUrl = "http://localhost:8080/chims/data";

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

    public String loginNew() {

//        if (userName == null || userName.trim().equals("")) {
//            JsfUtil.addErrorMessage("Please enter Username");
//            return "";
//        }
//        if (userPassword == null || userPassword.trim().equals("")) {
//            JsfUtil.addErrorMessage("Please enter Password");
//            return "";
//        }
//        if (!checkLoginNew()) {
//            JsfUtil.addErrorMessage("Username/Password Error. Please retry.");
//            return "";
//        }
//        loggedUserPrivileges = userPrivilegeList(loggedUser);
        ServiceConnector sc = new ServiceConnector();
        JSONObject obj = sc.GetRequest("http://localhost:8080/ProcedureRoomService/resources/lk.gov.health.procedureroomservice.procedureroom/count");

        userName = obj.get("room_count").toString();

        JsfUtil.addSuccessMessage("Successfully Logged");
        return "/index";
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
        Client client = Client.create();
        WebResource webResource1 = client.resource(mainAppUrl + "/get_role_name/" + this.userRole);
        ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
        return cr.getEntity(String.class);        
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

    public String getInsName() {
        Client client = Client.create();
        WebResource webResource1 = client.resource(mainAppUrl + "/get_institution_name/" + this.insId);
        ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
        return cr.getEntity(String.class);
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

    public String getPersonName() {
        Client client = Client.create();
        WebResource webResource1 = client.resource(mainAppUrl + "/get_user_name/" + this.userId);
        ClientResponse cr = webResource1.accept("application/json").get(ClientResponse.class);
        return cr.getEntity(String.class);
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
}
