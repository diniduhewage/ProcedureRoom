/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

import java.util.ArrayList;
import lk.gov.health.procedure.enums.ObjectStatus;
import org.json.simple.JSONArray;
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
    private String comment;
    private ObjectStatus status;

    public MedProcedurePojo(Long id, String proc_id_, String description_, ProcedureTypePojo proc_type_,  String comment_, ObjectStatus status_) {
        this.id = id;
        this.procId = proc_id_;
        this.description = description_;
        this.procType = proc_type_;
        this.comment = comment_;
        this.status = status_;
    }

    public MedProcedurePojo() {

    }

    public JSONObject getJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("procId", this.getProcId());
        jo_.put("description", this.getDescription());
        jo_.put("procType", getProcedureTypeJsonObject());
        jo_.put("comment", this.getComment());
        jo_.put("status", this.getStatus().toString());

        return jo_;
    } 
    
    public JSONObject getProcedureTypeJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("id", this.getProcType().getId());
        jo_.put("procedureType", this.getProcType().getProcedureType());
        jo_.put("description", this.getProcType().getDescription());
        
        return jo_;        
    }   

    public MedProcedurePojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setProcId(jo_.containsKey("procId") ? jo_.get("procId").toString() : null);
        this.setDescription(jo_.containsKey("description") ? jo_.get("description").toString() : null);
        this.setProcType(jo_.containsKey("procType") ? (getProcTypeObject(jo_.get("procType"))) : null);
        this.setComment(jo_.containsKey("comment") ? jo_.get("comment").toString() : null);
        this.setStatus(jo_.containsKey("status") ? ObjectStatus.valueOf(jo_.get("status").toString()) : null);
        return this;
    }
    
    public ProcedureRoomTypePojo getRoomTypeObject(Object obj){
 
      ProcedureRoomTypePojo pojoInst = new ProcedureRoomTypePojo();
      return pojoInst.getObject((JSONObject)obj);
    }
   
    public ProcedureTypePojo getProcTypeObject(Object obj){
 
      ProcedureTypePojo pojoInst = new ProcedureTypePojo();
      return pojoInst.getObject((JSONObject)obj);
    }
    public ArrayList<MedProcedurePojo> getObjectList(JSONArray ja_) {
        ArrayList<MedProcedurePojo> ObjectList = new ArrayList<>();
        
        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new MedProcedurePojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
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
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ObjectStatus getStatus() {
        return status;
    }

    public void setStatus(ObjectStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
