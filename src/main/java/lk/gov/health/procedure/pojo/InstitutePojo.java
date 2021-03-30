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
public class InstitutePojo {

    private Long id;
    private String code;
    private String intituteTypeDb;
    private String intituteType;
    private String name;
    private String hin;
    private String address;
    private String provinceId;
    private String districtId;

    public ArrayList<InstitutePojo> getObjectList(JSONArray ja_) {
        ArrayList<InstitutePojo> ObjectList = new ArrayList<>();

        for (int i = 0; i < ja_.size(); i++) {
            ObjectList.add(new InstitutePojo().getObject((JSONObject) ja_.get(i)));
        }
        return ObjectList;
    }

    public InstitutePojo getObject(JSONObject jo_) {
        this.setId(Long.parseLong(jo_.get("id").toString()));
        this.setCode(jo_.containsKey("code") ? jo_.get("code").toString() : null);
        this.setIntituteTypeDb(jo_.containsKey("institute_type_db") ? jo_.get("institute_type_db").toString() : null);
        this.setIntituteType(jo_.containsKey("institute_type") ? jo_.get("institute_type").toString() : null);
        this.setName(jo_.containsKey("name") ? jo_.get("name").toString() : null);
        this.setHin(jo_.containsKey("hin") ? jo_.get("hin").toString() : null);
        this.setAddress(jo_.containsKey("address") ? jo_.get("address").toString() : null);
        this.setProvinceId(jo_.containsKey("provinceId") ? jo_.get("provinceId").toString() : null);
        this.setDistrictId(jo_.containsKey("districtId") ? jo_.get("districtId").toString() : null);

        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHin() {
        return hin;
    }

    public void setHin(String hin) {
        this.hin = hin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getIntituteTypeDb() {
        return intituteTypeDb;
    }

    public void setIntituteTypeDb(String intituteTypeDb) {
        this.intituteTypeDb = intituteTypeDb;
    }

    public String getIntituteType() {
        return intituteType;
    }

    public void setIntituteType(String intituteType) {
        this.intituteType = intituteType;
    }

}
