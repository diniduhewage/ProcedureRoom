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
public class ProcedureRoomPojo {
    private String roomId;
    private String description;
    private Long type;
    private Long instituteId;
    private String status;
    
    public ProcedureRoomPojo(String room_id_, String description_, Long type_,Long institute_id_, String status_){
        this.roomId = room_id_;
        this.description = description_;
        this.type = type_;
        this.instituteId = institute_id_;
        this.status = status_;
    }
       
    public JSONObject getJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("roomId", this.getRoomId());
        jo_.put("description", this.getDescription());
        jo_.put("type", this.getType());
        jo_.put("instituteId", this.getInstituteId());
        jo_.put("status", this.getStatus());
        
        return jo_;        
    }
            

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }  
    
    
}
