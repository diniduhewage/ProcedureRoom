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
 * @author sandasala
 */
public class DrugFrequencyPojo {
    private Long id;
    private String frequency;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DrugFrequencyPojo() {
    }

    public DrugFrequencyPojo(Long id, String frequency, String description) {
        this.id = id;
        this.frequency = frequency;
        this.description = description;
    }

    public JSONObject getJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("frequency", this.getFrequency());
        jo_.put("description", this.getDescription());
        return jo_;
    }

    public DrugFrequencyPojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setFrequency(jo_.containsKey("frequency") ? jo_.get("frequency").toString() : null);
        this.setDescription(jo_.containsKey("description") ? jo_.get("description").toString() : null);
        return this;
    }

    public ArrayList<DrugFrequencyPojo> getObjectList(JSONArray ja_) {
        ArrayList<DrugFrequencyPojo> ObjectList = new ArrayList<>();

        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new DrugFrequencyPojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }    
    
}
