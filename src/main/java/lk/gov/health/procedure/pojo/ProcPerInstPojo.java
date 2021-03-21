/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.pojo;

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
}
