/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.util;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import lk.gov.health.procedure.bean.ProcedureTypeCtrl;
import lk.gov.health.procedure.pojo.ProcedureTypePojo;

/**
 *
 * @author user
 */
@Named
@FacesConverter(value = "procedureTypeConverter", managed = true)
@ApplicationScoped
public class ProcedureTypeConverter implements Converter<ProcedureTypePojo> {

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, ProcedureTypePojo value) {
        if (value != null) {
            return String.valueOf(value.getId());
        } else {
            return null;
        }
    }

    @Override
    public ProcedureTypePojo getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                ProcedureTypeCtrl proType = new ProcedureTypeCtrl();
                proType.getProcTypes();
                for (ProcedureTypePojo item : proType.getItems()) {
                    if (item.getId() == Long.parseLong(value)) {
                        return item;
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid country."));
            }
        } else {
            return null;
        }
    }

}
