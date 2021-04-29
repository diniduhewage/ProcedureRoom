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
public class ProcedureGroupPerInstitutePojo {
    private Long id;
    private InstitutePojo institute;
    private ProcedureGroupPojo procedureGroup;
    
    public ArrayList<ProcedureGroupPerInstitutePojo> getObjectList(JSONArray ja_) {
        ArrayList<ProcedureGroupPerInstitutePojo> ObjectList = new ArrayList<>();

        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new ProcedureGroupPerInstitutePojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }
    
    public ProcedureGroupPerInstitutePojo getObject(JSONObject jo_) {
        System.out.println("dinidu -->"+jo_.toString());
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setProcedureGroup(jo_.containsKey("procGroup") ? getProcedureGroupObject(jo_.get("procGroup")) : null);
        this.setInstitute(jo_.containsKey("institute") ? getInstituteObject(jo_.get("institute")) : null);        
        return this;
    } 
    
    public ProcedureGroupPojo getProcedureGroupObject(Object obj){ 
      ProcedureGroupPojo pg = new ProcedureGroupPojo();
      return pg.getObject((JSONObject)obj);
    }
    
    public InstitutePojo getInstituteObject(Object obj){ 
      InstitutePojo pg = new InstitutePojo();
      return pg.getObject((JSONObject)obj);
    }
    
    public JSONObject getJsonObject() {
        JSONObject jo_ = new JSONObject();
        jo_.put("procedureGroup", this.getGroupJsonObject());
        jo_.put("institute", this.getInstituteJSONObject());
        return jo_;
    }
    
    public JSONObject getGroupJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("id", this.getProcedureGroup().getId());
        jo_.put("procGroup", this.getProcedureGroup().getProcGroup());
        jo_.put("description", this.getProcedureGroup().getDescription());
        
        return jo_;
    }
    
    private JSONObject getInstituteJSONObject() {
        JSONObject jo_ = new JSONObject();
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

    public ProcedureGroupPojo getProcedureGroup() {
        return procedureGroup;
    }

    public void setProcedureGroup(ProcedureGroupPojo procedureGroup) {
        this.procedureGroup = procedureGroup;
    }  
}
