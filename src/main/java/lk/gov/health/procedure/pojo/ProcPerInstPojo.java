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
public class ProcPerInstPojo {
    private Long id;
    private InstitutePojo institute;
    private MedProcedurePojo procedure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InstitutePojo getInstitute() {
        return institute;
    }

    public void setInstitute(InstitutePojo institute) {
        this.institute = institute;
    }

    public MedProcedurePojo getProcedure() {
        return procedure;
    }

    public void setProcedure(MedProcedurePojo procedure) {
        this.procedure = procedure;
    }  
    
    public ArrayList<ProcPerInstPojo> getObjectList(JSONArray ja_) {
        ArrayList<ProcPerInstPojo> ObjectList = new ArrayList<>();

        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new ProcPerInstPojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }
    
    public ProcPerInstPojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setInstitute(jo_.containsKey("institute") ? getInstituteObject(jo_.get("institute")) : null);
        this.setProcedure(jo_.containsKey("procedure") ? getMedProcedureObject(jo_.get("procedure")) : null);        
        return this;
    } 
    
    public MedProcedurePojo getMedProcedureObject(Object obj){ 
      MedProcedurePojo medProcedure = new MedProcedurePojo();
      return medProcedure.getObject((JSONObject)obj);
    }
    
    public InstitutePojo getInstituteObject(Object obj){ 
      InstitutePojo pg = new InstitutePojo();
      return pg.getObject((JSONObject)obj);
    }

    public JSONObject getJsonObject(MedProcedurePojo proc_,InstitutePojo institute) {
        this.setProcedure(proc_);
        this.setInstitute(institute);
        
        JSONObject jo_ = new JSONObject();
        jo_.put("procedure", this.getMedProcedureJsonObject());
        jo_.put("instituteId", this.getInstituteJSONObject());
        return jo_;
    }
    
    public JSONObject getMedProcedureJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("id", this.getProcedure().getId());
        jo_.put("procId", this.getProcedure().getProcId());
        jo_.put("description", this.getProcedure().getDescription());
        jo_.put("procType", getProcedureTypeJsonObject());
        jo_.put("comment", this.getProcedure().getComment());
        jo_.put("status", "ACTIVE");

        return jo_;
    } 
    
    public JSONObject getProcedureTypeJsonObject(){
        JSONObject jo_ = new JSONObject();
        
        jo_.put("id", this.getProcedure().getProcType().getId());
        jo_.put("procedureType", this.getProcedure().getProcType().getProcedureType());
        jo_.put("description", this.getProcedure().getProcType().getDescription());
        
        return jo_;        
    }
    
    private JSONObject getInstituteJSONObject() {
        JSONObject jo_ = new JSONObject();
        System.out.println("zzzzzzzzzzzz -->"+this.getInstitute().getId());
        jo_.put("id", this.getInstitute().getId());
        jo_.put("code", this.getInstitute().getCode());
        jo_.put("name", this.getInstitute().getName());
        jo_.put("hin", this.getInstitute().getHin());
        jo_.put("address", this.getInstitute().getAddress());
        jo_.put("provinceId", this.getInstitute().getProvinceId());
        jo_.put("districtId", this.getInstitute().getDistrictId());
        jo_.put("childInstitutes", this.getInstitute().getChildInstitutes());
        jo_.put("editedAt", this.getInstitute().getEditedAt().toString());

        return jo_;
    }
}
