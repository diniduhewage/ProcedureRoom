/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

import java.util.Date;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class ProcedurePerClientPojo {
    private String phn;
    private Long instituteId;
    private Long procedureId;
    private Long roomId;
    private Long createdBy; 
    private Date createdAt;
    private String status;
    
    public ProcedurePerClientPojo(String phn_,Long institute_id_,Long procedure_id_,Long room_id_,Long created_by_,Date created_at_ , String status_){
        this.phn = phn_;
        this.instituteId = institute_id_;
        this.procedureId = procedure_id_;
        this.roomId = room_id_;
        this.createdBy = created_by_;
        this.createdAt = created_at_;
        this.status = status_;
    }
    
    public JSONObject getJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("phn", this.getPhn());
        jo_.put("instituteId", this.getInstituteId());
        jo_.put("procedureId", this.getProcedureId());
        jo_.put("roomId", this.getRoomId());
        jo_.put("createdBy", this.getCreatedBy());
        jo_.put("createdAt", this.getCreatedAt());
        jo_.put("status", this.getStatus());
        
        return jo_;        
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public Long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
