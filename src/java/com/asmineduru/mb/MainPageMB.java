package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Brand;
import com.asmineduru.model.Likes;
import com.asmineduru.model.Product;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@ViewScoped
public class MainPageMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    @ManagedProperty(value = "#{memberSessionMB}")
    private MemberSessionMB memberSessionMB;

    private List<Brand> brandList;
    private List<Product> leftSideProductList;
    private List<Product> mainPageProductList;

    private Product productOne;
    private Product productTwo;
    private Product productThree;

    @PostConstruct
    public void init() {
        try {

            leftSideProductList = mainDao.findLeftSideProductInUsage();
            mainPageProductList = mainDao.findMainPageProductInUsage();
            
            if (memberSessionMB.getMember() != null) {
                List<Likes> likes = mainDao.findLikeByMember(memberSessionMB.getMember().getMemberId());
                
                if (likes!=null && !likes.isEmpty()) {
                    
                    if (mainPageProductList!=null && !mainPageProductList.isEmpty()) {
                        
                        for (Product product : mainPageProductList) {
                            
                            for (Likes like : likes) {
                                
                                if (product.getProductId().equals(like.getProductId())) {
                                    
                                    product.setMemberFavorite(true);
                                    
                                }
                            }                            
                        }
                    }                    
                }                
            }
            
            brandList = mainDao.findAllBrandInUsage();

            List<Product> productMaxDiscountList = mainDao.findProductListOrderByMaxDiscountInUsage();

            if (productMaxDiscountList != null && !productMaxDiscountList.isEmpty()) {
                productOne = productMaxDiscountList.get(0);
                if (productMaxDiscountList.size() > 0) {
                    productTwo = productMaxDiscountList.get(1);
                }

                if (productMaxDiscountList.size() > 1) {
                    productThree = productMaxDiscountList.get(2);
                }
            }

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

    public Product getProductOne() {
        return productOne;
    }

    public void setProductOne(Product productOne) {
        this.productOne = productOne;
    }

    public Product getProductTwo() {
        return productTwo;
    }

    public void setProductTwo(Product productTwo) {
        this.productTwo = productTwo;
    }

    public Product getProductThree() {
        return productThree;
    }

    public void setProductThree(Product productThree) {
        this.productThree = productThree;
    }

    public MemberSessionMB getMemberSessionMB() {
        return memberSessionMB;
    }

    public void setMemberSessionMB(MemberSessionMB memberSessionMB) {
        this.memberSessionMB = memberSessionMB;
    }

}
