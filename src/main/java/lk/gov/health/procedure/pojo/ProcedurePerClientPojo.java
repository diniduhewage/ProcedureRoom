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
    private ProcPerInstPojo procedureId;
    private Long createdBy; 
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date createdAt;
    private ProcPerClientStates status; 
    
    public JSONObject getJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("phn", this.getPhn());
        jo_.put("instituteId", this.getInstituteId().getCode());
        jo_.put("procedureCode", this.getProcedureId().getProcedure().getProcId());
        jo_.put("createdBy", this.getCreatedBy());
        
        return jo_;         
    } 
    
    public JSONObject getProcedureJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("id", this.getProcedureId().getProcedure().getId());
        jo_.put("procId", this.getProcedureId().getProcedure().getProcId());
        jo_.put("description", this.getProcedureId().getProcedure().getDescription());
        jo_.put("procType", this.getProcedureId().getProcedure().getProcedureTypeJsonObject());
        jo_.put("comment", this.getProcedureId().getProcedure().getComment());
        jo_.put("status", this.getProcedureId().getProcedure().getStatus().toString());
        
        return jo_;        
    } 
    
    private JSONObject getInstituteJSONObject() {
        JSONObject jo_ = new JSONObject();
        jo_.put("id", this.getInstituteId().getId());
        jo_.put("code", this.getInstituteId().getCode());
        jo_.put("name", this.getInstituteId().getName());
        jo_.put("hin", this.getInstituteId().getHin());
        jo_.put("address", this.getInstituteId().getAddress());
        jo_.put("provinceId", this.getInstituteId().getProvinceId());
        jo_.put("districtId", this.getInstituteId().getDistrictId());
        jo_.put("childInstitutes", this.getInstituteId().getChildInstitutes());
        jo_.put("editedAt", this.getInstituteId().getEditedAt().toString());

        return jo_;
    } 
    
    public ProcedurePerClientPojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setPhn(jo_.containsKey("phn") ? jo_.get("phn").toString() : null);
        this.setInstituteId(jo_.containsKey("instituteId") ? getInstituteObject(jo_.get("instituteId")) : null);
        this.setProcedureId(jo_.containsKey("procedureId") ? getMedProcedureObject(jo_.get("procedureId")) : null);
        this.setCreatedBy(jo_.containsKey("createdBy") ? Long.parseLong(jo_.get("createdBy").toString()) : null);
        try {        
            this.setCreatedAt(jo_.containsKey("createdAt") ? new SimpleDateFormat("yyyy-MM-dd").parse(jo_.get("createdAt").toString()) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProcedurePerClientPojo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setStatus(jo_.containsKey("status") ? ProcPerClientStates.valueOf(jo_.get("status").toString()) : null); 
        
        return this;
    } 
    
    public ProcPerInstPojo getMedProcedureObject(Object obj){ 
      ProcPerInstPojo medProcedure = new ProcPerInstPojo();
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

    public ProcPerInstPojo getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(ProcPerInstPojo procedureId) {
        this.procedureId = procedureId;
    }
    
}
