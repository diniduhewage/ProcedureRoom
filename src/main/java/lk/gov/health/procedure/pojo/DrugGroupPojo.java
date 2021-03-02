/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

import java.util.ArrayList;
import lk.gov.health.procedure.util.ServiceConnector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class DrugGroupPojo {

    private Long id;
    private String drugGroup;
    private String description;
    private DrugGroupPojo parentGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrugGroup() {
        return drugGroup;
    }

    public void setDrugGroup(String drugGroup) {
        this.drugGroup = drugGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DrugGroupPojo getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(DrugGroupPojo parentGroup) {
        this.parentGroup = parentGroup;
    }

    public DrugGroupPojo(Long id, String drugGroup, String description, DrugGroupPojo parentGroup) {
        this.id = id;
        this.drugGroup = drugGroup;
        this.description = description;
        this.parentGroup = parentGroup;
    }

    public DrugGroupPojo() {
    }

    public JSONObject getJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("drugGroup", this.getDrugGroup());
        jo_.put("description", this.getDescription());
        if (this.parentGroup != null) {
            jo_.put("parentGroup", getParentGroupJsonObject());
        } else {
            jo_.put("parentGroup", null);
        }
        return jo_;
    }

    public JSONObject getParentGroupJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("id", this.getParentGroup().getId());
        jo_.put("drugGroup", this.getParentGroup().getDrugGroup());
        jo_.put("description", this.getParentGroup().getDescription());
        jo_.put("parentGroup", null);
        return jo_;
    }

    public DrugGroupPojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setDrugGroup(jo_.containsKey("drugGroup") ? jo_.get("drugGroup").toString() : null);
        this.setDescription(jo_.containsKey("description") ? jo_.get("description").toString() : null);
        this.setParentGroup(null);
        return this;
    }

    public DrugGroupPojo getParentGroupObject(Object obj) {
        if(obj != null){
            return null;
        }
        return this.getObject((JSONObject) obj);
    }

    public ArrayList<DrugGroupPojo> getObjectList(JSONArray ja_) {
        ArrayList<DrugGroupPojo> ObjectList = new ArrayList<>();

        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new DrugGroupPojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }

}
