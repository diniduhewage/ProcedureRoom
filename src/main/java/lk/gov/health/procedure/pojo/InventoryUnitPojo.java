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
public class InventoryUnitPojo {

    private Long id;
    private String inventoryUnit;
    private String quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInventoryUnit() {
        return inventoryUnit;
    }

    public void setInventoryUnit(String inventoryUnit) {
        this.inventoryUnit = inventoryUnit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public InventoryUnitPojo(Long id, String inventoryUnit, String quantity) {
        this.id = id;
        this.inventoryUnit = inventoryUnit;
        this.quantity = quantity;
    }

    public InventoryUnitPojo() {
    }

    public JSONObject getJsonObject() {
        JSONObject jo_ = new JSONObject();

        jo_.put("inventoryUnit", this.getInventoryUnit());
        jo_.put("quantity", this.getQuantity());
        return jo_;
    }

    public InventoryUnitPojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setInventoryUnit(jo_.containsKey("inventoryUnit") ? jo_.get("inventoryUnit").toString() : null);
        this.setQuantity(jo_.containsKey("quantity") ? jo_.get("quantity").toString() : null);
        return this;
    }

    public ArrayList<InventoryUnitPojo> getObjectList(JSONArray ja_) {
        ArrayList<InventoryUnitPojo> ObjectList = new ArrayList<>();

        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new InventoryUnitPojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }

}
