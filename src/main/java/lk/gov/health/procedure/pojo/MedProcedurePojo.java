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

    private Long id;
    private String procId;
    private String description;
    private ProcedureTypePojo procType;
    private ProcedureRoomTypePojo roomType;
    private String comment;
    private String status;

    public MedProcedurePojo(Long id, String proc_id_, String description_, ProcedureTypePojo proc_type_, ProcedureRoomTypePojo room_type_, String comment_, String status_) {
        this.id = id;
        this.procId = proc_id_;
        this.description = description_;
        this.procType = proc_type_;
        this.roomType = room_type_;
        this.comment = comment_;
        this.status = status_;
    }

    public MedProcedurePojo() {

    }

    public JSONObject getJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("procId", this.getProcId());
        jo_.put("description", this.getDescription());
        jo_.put("procType", this.getProcType());
        jo_.put("roomType", this.getRoomType());
        jo_.put("comment", this.getComment());
        jo_.put("status", this.getStatus());

        return jo_;
    }

    public MedProcedurePojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setProcId(jo_.containsKey("procId") ? jo_.get("procId").toString() : null);
        this.setDescription(jo_.containsKey("description") ? jo_.get("description").toString() : null);
        this.setProcType(jo_.containsKey("procType") ? (ProcedureTypePojo)(jo_.get("procType")) : null);
        this.setRoomType(jo_.containsKey("roomType") ? (ProcedureRoomTypePojo)(jo_.get("roomType")) : null);
        this.setComment(jo_.containsKey("comment") ? jo_.get("comment").toString() : null);
        this.setStatus(jo_.containsKey("status") ? jo_.get("status").toString() : null);
        return this;
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

    public ProcedureTypePojo getProcType() {
        return procType;
    }

    public void setProcType(ProcedureTypePojo procType) {
        this.procType = procType;
    }

    public ProcedureRoomTypePojo getRoomType() {
        return roomType;
    }

    public void setRoomType(ProcedureRoomTypePojo roomType) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
