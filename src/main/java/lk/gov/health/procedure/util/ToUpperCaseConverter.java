/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.procedure.util;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author user
 */
@Named
@FacesConverter(value = "toUpperCaseConverter", managed = true)
@ApplicationScoped
public class ToUpperCaseConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        return (value != null) ? value.toUpperCase() : null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        return (obj != null) ? obj.toString() : "";
    }    
}
