/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lk.gov.health.procedure.enums.ProcPerClientStates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class ProcedurePerClientPojo {
    private Long id;
    private String phn;
    private InstitutePojo instituteId;
    private MedProcedurePojo procedureId;
    private ProcedureRoomPojo roomId;
    private Long createdBy; 
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date createdAt;
    private ProcPerClientStates status;
    
    public ProcedurePerClientPojo(){
        
    }
    
    public ProcedurePerClientPojo(String phn_,InstitutePojo institute_id_,MedProcedurePojo procedure_id_,ProcedureRoomPojo room_id_,Long created_by_,Date created_at_ , ProcPerClientStates status_){
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
        jo_.put("procedureId", this.getProcedureJsonObject());
        jo_.put("roomId", this.getRoomJsonObject());
        jo_.put("createdBy", this.getCreatedBy());
        jo_.put("status", this.getStatus().toString());
        
        return jo_;        
    }
    
    public JSONObject getProcedureJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("id", this.getProcedureId().getId());
        jo_.put("procId", this.getProcedureId().getProcId());
        jo_.put("description", this.getProcedureId().getDescription());
        jo_.put("procType", this.getProcedureId().getProcedureTypeJsonObject());
        jo_.put("roomType", this.getProcedureId().getRoomTypeJsonObject());
        jo_.put("comment", this.getProcedureId().getComment());
        jo_.put("status", this.getProcedureId().getStatus().toString());
        
        return jo_;        
    }
    
    public JSONObject getRoomJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("id", this.getRoomId().getId());
        jo_.put("roomId", this.getRoomId().getRoomId());
        jo_.put("description", this.getRoomId().getDescription());
        jo_.put("type", this.getRoomId().getRoomTypeJsonObject());
        jo_.put("instituteId", this.getRoomId().getInstituteId());
        jo_.put("status", this.getRoomId().getStatus().toString());
        
        return jo_;        
    }
    
    public ProcedurePerClientPojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setPhn(jo_.containsKey("phn") ? jo_.get("phn").toString() : null);
        this.setInstituteId(jo_.containsKey("instituteId") ? getInstituteObject(jo_.get("instituteId")) : null);
        this.setProcedureId(jo_.containsKey("procedureId") ? getMedProcedureObject(jo_.get("procedureId")) : null);
        this.setRoomId(jo_.containsKey("roomId") ? getProcRoomObject(jo_.get("roomId")) : null);
        this.setCreatedBy(jo_.containsKey("createdBy") ? Long.parseLong(jo_.get("createdBy").toString()) : null);
        try {        
            this.setCreatedAt(jo_.containsKey("createdAt") ? new SimpleDateFormat("yyyy-MM-dd").parse(jo_.get("createdAt").toString()) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProcedurePerClientPojo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setStatus(jo_.containsKey("status") ? ProcPerClientStates.valueOf(jo_.get("status").toString()) : null); 
        
        return this;
    } 
    
    public MedProcedurePojo getMedProcedureObject(Object obj){ 
      MedProcedurePojo medProcedure = new MedProcedurePojo();
      return medProcedure.getObject((JSONObject)obj);
    }
    
    public ProcedureRoomPojo getProcRoomObject(Object obj){ 
      ProcedureRoomPojo procRoom = new ProcedureRoomPojo();
      return procRoom.getObject((JSONObject)obj);
    }
    
    private InstitutePojo getInstituteObject(Object obj) {
        InstitutePojo inst = new InstitutePojo();
        return inst.getObject((JSONObject)obj);
    }
    
    public ArrayList<ProcedurePerClientPojo> getObjectList(JSONArray ja_) {
        ArrayList<ProcedurePerClientPojo> ObjectList = new ArrayList<>();
        
        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new ProcedurePerClientPojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }
    
    
    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public InstitutePojo getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(InstitutePojo instituteId) {
        this.instituteId = instituteId;
    }

    public MedProcedurePojo getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(MedProcedurePojo procedureId) {
        this.procedureId = procedureId;
    }

    public ProcedureRoomPojo getRoomId() {
        return roomId;
    }

    public void setRoomId(ProcedureRoomPojo roomId) {
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

    public ProcPerClientStates getStatus() {
        return status;
    }

    public void setStatus(ProcPerClientStates status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }  
    
}
