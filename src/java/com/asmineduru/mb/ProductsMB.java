package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Product;
import com.asmineduru.model.Type;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@ViewScoped
public class ProductsMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private List<Product> productList;
    private Type selectedType;
    private Integer typeId;

     @PostConstruct
    public void init() {
        try {

            productList = new ArrayList<>();
            
        } catch (Exception e) {
        }
    }
    
    public void loadData() {
        try {

            selectedType = mainDao.findTypeByTypeId(typeId);
            productList = mainDao.findProductByTypeIdInUsage(selectedType.getTypeId());

        } catch (Exception e) {

        }
    }

///////////////////// Getter ve Setter ////////////////////////////////////////
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Type getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(Type selectedType) {
        this.selectedType = selectedType;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }



}
