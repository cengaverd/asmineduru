package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Brand;
import com.asmineduru.model.Product;
import com.asmineduru.model.Type;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@ViewScoped
public class ProductMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private Type type;
    
    
    public String navigateProducts(Type type) {
        try {

            this.type=type;

        } catch (Exception e) {
            
        }
        
        return "products.xhtml";
    }

///////////////////// Getter ve Setter ////////////////////////////////////////

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


}
