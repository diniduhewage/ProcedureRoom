/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.enums;

/**
 *
 * @author user
 */
public enum UserRole {
    System_Administrator("System Administrator"),
    Super_User("Super User"),
    User("User"),
    Institution_User("Institution User"),
    Institution_Super_User("Institution Super User"),
    Institution_Administrator("Institution Administrator"),
    Me_User("Monitoring & Evaluation Administrator"),
    Me_Super_User("Monitoring & Evaluation Super User"),
    Me_Admin("Monitoring & Evaluation User"),
    Doctor("Doctor"),
    Nurse("Nurse"),
    Midwife("Midwife"),
    Client("Client");

    private final String label;
    
    private UserRole(String label){
        this.label = label;
    }
    
    public String getLabel(){
        return label;
    } 
}
