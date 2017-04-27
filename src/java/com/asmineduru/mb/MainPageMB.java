package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Brand;
import com.asmineduru.model.Product;
import com.asmineduru.model.Type;
import java.io.Serializable;
import java.util.ArrayList;
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
public class MainPageMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private List<Brand> brandList;
    private List<Product> leftSideProductList;
    private List<Product> mainPageProductList;
    private boolean showMainPage;

    private List<Product> productList;
    private Type selectedType;
    
    @PostConstruct
    public void init() {
        try {

            leftSideProductList = mainDao.findLeftSideProductInUsage();
            mainPageProductList = mainDao.findMainPageProductInUsage();
            brandList = mainDao.findAllBrandInUsage();
            showMainPage=true;
            productList=new ArrayList<>();
        } catch (Exception e) {
        }
    }
    
    public void navigateProducts(Type type) {
        try {

           selectedType=type;
           productList = mainDao.findProductByTypeIdInUsage(type.getTypeId());
           showMainPage=false;

        } catch (Exception e) {
            
        }        
    }

///////////////////// Getter ve Setter ////////////////////////////////////////
    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Product> getLeftSideProductList() {
        return leftSideProductList;
    }

    public void setLeftSideProductList(List<Product> leftSideProductList) {
        this.leftSideProductList = leftSideProductList;
    }

    public List<Product> getMainPageProductList() {
        return mainPageProductList;
    }

    public void setMainPageProductList(List<Product> mainPageProductList) {
        this.mainPageProductList = mainPageProductList;
    }

    public boolean isShowMainPage() {
        return showMainPage;
    }

    public void setShowMainPage(boolean showMainPage) {
        this.showMainPage = showMainPage;
    }

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

}
