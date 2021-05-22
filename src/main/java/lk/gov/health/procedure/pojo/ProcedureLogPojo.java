/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

import java.util.Date;

/**
 *
 * @author user
 */
public class ProcedureLogPojo {
    private Long id;
    private ProcedurePerClientPojo procedurePerClient;
    private Date createdAt;
    private String activity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcedurePerClientPojo getProcedurePerClient() {
        return procedurePerClient;
    }

    public void setProcedurePerClient(ProcedurePerClientPojo procedurePerClient) {
        this.procedurePerClient = procedurePerClient;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
