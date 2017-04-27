package com.asmineduru.converter;

import com.asmineduru.model.Brand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Brand.class)
public class BrandConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Brand) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Brand) {
            Brand entity = (Brand) value;
            if (entity instanceof Brand && entity.getBrandId()!= null) {
                uiComponent.getAttributes().put(entity.getBrandId().toString(), entity);
                return entity.getBrandId().toString();
            }
        }
        return "";
    }
}