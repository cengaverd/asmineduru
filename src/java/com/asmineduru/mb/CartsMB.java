package com.asmineduru.mb;


import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Cart;
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
public class CartsMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private List<Cart> cartList;
    private Cart selectedCart;

    @PostConstruct
    public void init() {
        try {

            cartList = mainDao.findAllCart();

        } catch (Exception e) {
        }
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public Cart getSelectedCart() {
        return selectedCart;
    }

    public void setSelectedCart(Cart selectedCart) {
        this.selectedCart = selectedCart;
    }
    
}
