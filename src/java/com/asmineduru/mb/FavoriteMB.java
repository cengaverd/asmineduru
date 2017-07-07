package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Brand;
import com.asmineduru.model.Likes;
import com.asmineduru.model.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@RequestScoped
public class FavoriteMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    @ManagedProperty(value = "#{memberSessionMB}")
    private MemberSessionMB memberSessionMB;

    private List<Product> favoriteProductList;

    @PostConstruct
    public void init() {
        try {
            
            favoriteProductList = new ArrayList<>();

            if (memberSessionMB.getMember() != null) {
                List<Likes> likes = mainDao.findLikeByMember(memberSessionMB.getMember().getMemberId());

                if (likes != null && !likes.isEmpty()) {

                    for (Likes like : likes) {
                        
                        Product product=mainDao.findProductByProductId(like.getProductId());
                        product.setMemberFavorite(true);
                        favoriteProductList.add(product);
                    }
                }
            }

        } catch (Exception e) {
        }
    }
    
    public String disLike(Product product) {
        try {
            if (memberSessionMB.getMember() != null) {

                Likes like = mainDao.findLikeByMemberAndProduct(memberSessionMB.getMember().getMemberId(), product.getProductId());

                if (like != null) {
                    
                    mainDao.deleteObject(like);                    
                    product.setMemberFavorite(false);
                    favoriteProductList.remove(product);
                }

            } 
        } catch (Exception e) {
        }
        return null;
    }

///////////////////// Getter ve Setter ////////////////////////////////////////

    public MemberSessionMB getMemberSessionMB() {
        return memberSessionMB;
    }

    public void setMemberSessionMB(MemberSessionMB memberSessionMB) {
        this.memberSessionMB = memberSessionMB;
    }

    public List<Product> getFavoriteProductList() {
        return favoriteProductList;
    }

    public void setFavoriteProductList(List<Product> favoriteProductList) {
        this.favoriteProductList = favoriteProductList;
    }
   

}
