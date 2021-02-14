/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class MedProcedurePojo {
    private String procId;
    private String description;
    private Long procType;
    private Long roomType;
    private String comment;
    private String status;
    
    public MedProcedurePojo(String proc_id_,String description_,Long proc_type_,Long room_type_, String comment_,String status_){
        this.procId = proc_id_;
        this.description=description_;
        this.procType = proc_type_;
        this.roomType = room_type_;
        this.comment= comment_;
        this.status = status_;
    }
    
    public JSONObject getJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("procId", this.getProcId());
        jo_.put("description", this.getDescription());
        jo_.put("procType", this.getProcType());
        jo_.put("roomType", this.getRoomType());
        jo_.put("comment", this.getComment());
        jo_.put("status", this.getStatus());
        
        return jo_;        
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProcType() {
        return procType;
    }

    public void setProcType(Long procType) {
        this.procType = procType;
    }

    public Long getRoomType() {
        return roomType;
    }

    public void setRoomType(Long roomType) {
        this.roomType = roomType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
