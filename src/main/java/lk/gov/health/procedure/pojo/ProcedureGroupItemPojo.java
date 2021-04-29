/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author user
 */
public class ProcedureGroupItemPojo {
    private Long id;
    private ProcedureGroupPojo procGroup;
    private MedProcedurePojo procedure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcedureGroupPojo getProcGroup() {
        return procGroup;
    }

    public void setProcGroup(ProcedureGroupPojo procGroup) {
        this.procGroup = procGroup;
    }

    public MedProcedurePojo getProcedure() {
        return procedure;
    }

    public void setProcedure(MedProcedurePojo procedure) {
        this.procedure = procedure;
    } 
    
    public ArrayList<ProcedureGroupItemPojo> getObjectList(JSONArray ja_) {
        ArrayList<ProcedureGroupItemPojo> ObjectList = new ArrayList<>();

        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new ProcedureGroupItemPojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }
    
    public JSONObject getJsonObject() {
        JSONObject jo_ = new JSONObject();
        jo_.put("procGroup", this.getGroupJsonObject());
        jo_.put("procedure", this.getMedProcedureJsonObject());
        return jo_;
    }
    
    public JSONObject getGroupJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("id", this.getProcGroup().getId());
        jo_.put("procGroup", this.getProcGroup().getProcGroup());
        jo_.put("description", this.getProcGroup().getDescription());
        
        return jo_;
    }
    
    public JSONObject getMedProcedureJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("id", this.getProcedure().getId());
        jo_.put("procId", this.getProcedure().getProcId());
        jo_.put("description", this.getProcedure().getDescription());
        jo_.put("procType", getProcedureTypeJsonObject());
        jo_.put("comment", this.getProcedure().getComment());
        jo_.put("status", this.getProcedure().getStatus().toString());

        return jo_;
    } 
    
    public JSONObject getProcedureTypeJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("id", this.getProcedure().getProcType().getId());
        jo_.put("procedureType", this.getProcedure().getProcType().getProcedureType());
        jo_.put("description", this.getProcedure().getProcType().getDescription());
        
        return jo_;        
    }
    
    public ProcedureGroupItemPojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setProcGroup(jo_.containsKey("procGroup") ? getProcedureGroupObject(jo_.get("procGroup")) : null);
        this.setProcedure(jo_.containsKey("procedure") ? getMedProcedureObject(jo_.get("procedure")) : null);        
        return this;
    } 
    
    public MedProcedurePojo getMedProcedureObject(Object obj){ 
      MedProcedurePojo medProcedure = new MedProcedurePojo();
      return medProcedure.getObject((JSONObject)obj);
    }
    
    public ProcedureGroupPojo getProcedureGroupObject(Object obj){ 
      ProcedureGroupPojo pg = new ProcedureGroupPojo();
      return pg.getObject((JSONObject)obj);
    }
}
