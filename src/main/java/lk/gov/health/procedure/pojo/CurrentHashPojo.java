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
public class CurrentHashPojo {
    private Long id;
    private String currHash;
    private String owner;
    
    public ArrayList<CurrentHashPojo> getObjectList(JSONArray ja_) {
        ArrayList<CurrentHashPojo> ObjectList = new ArrayList<>();
        
        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new CurrentHashPojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }
    
    public CurrentHashPojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setCurrHash(jo_.containsKey("currHash") ? jo_.get("currHash").toString() : null);
        this.setOwner(jo_.containsKey("owner") ? jo_.get("owner").toString() : null);        
        return this;
    }  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrHash() {
        return currHash;
    }

    public void setCurrHash(String currHash) {
        this.currHash = currHash;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    
}
