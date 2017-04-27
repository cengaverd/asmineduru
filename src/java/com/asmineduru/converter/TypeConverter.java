package com.asmineduru.converter;

import com.asmineduru.model.Type;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Type.class)
public class TypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Type) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Type) {
            Type entity = (Type) value;
            if (entity instanceof Type && entity.getTypeId()!= null) {
                uiComponent.getAttributes().put(entity.getTypeId().toString(), entity);
                return entity.getTypeId().toString();
            }
        }
        return "";
    }
}