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
import javax.inject.Named;
import lk.gov.health.procedure.entity.Item;
import lk.gov.health.procedure.entity.UserPrivilege;
import lk.gov.health.procedure.entity.WebUser;
import lk.gov.health.procedure.facade.UserPrivilegeFacade;
import lk.gov.health.procedure.facade.WebUserFacade;
import lk.gov.health.procedure.facade.util.JsfUtil;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 *
 * @author user
 */
@Named
@SessionScoped
public class WebUserCtrl implements Serializable {

    @EJB
    private WebUserFacade ejbFacade;
    @EJB
    private UserPrivilegeFacade userPrivilegeFacade;

    private String userName;
    private String userPassword;
    private WebUser loggedUser;
    private List<UserPrivilege> loggedUserPrivileges;

    public String loginNew() {

        if (userName == null || userName.trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter Username");
            return "";
        }

        if (userPassword == null || userPassword.trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter Password");
            return "";
        }

        if (!checkLoginNew()) {
            JsfUtil.addErrorMessage("Username/Password Error. Please retry.");
            return "";
        }

        loggedUserPrivileges = userPrivilegeList(loggedUser);
        JsfUtil.addSuccessMessage("Successfully Logged");
        return "/index";
    }

    private boolean checkLoginNew() {
        if (getEjbFacade() != null) {
            String jpql_ = "SELECT u FROM WebUser u WHERE lower(u.name)=:userName and u.retired =:ret";

            Map m = new HashMap();
            m.put("userName", userName.trim().toLowerCase());
            m.put("ret", false);

            loggedUser = getEjbFacade().findFirstByJpql(jpql_, m);
            if (loggedUser != null) {
                return matchPassword(userPassword, loggedUser.getWebUserPassword());
            } else {
                JsfUtil.addErrorMessage("Server Error");
            }
        }
        loggedUser = null;
        return false;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public WebUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(WebUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    public UserPrivilegeFacade getUserPrivilegeFacade() {
        return userPrivilegeFacade;
    }

    public void setUserPrivilegeFacade(UserPrivilegeFacade userPrivilegeFacade) {
        this.userPrivilegeFacade = userPrivilegeFacade;
    }

    public List<UserPrivilege> getLoggedUserPrivileges() {
        return loggedUserPrivileges;
    }

    public void setLoggedUserPrivileges(List<UserPrivilege> loggedUserPrivileges) {
        this.loggedUserPrivileges = loggedUserPrivileges;
    }

}
