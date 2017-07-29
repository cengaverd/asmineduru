package com.asmineduru.mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

    private static final long serialVersionUID = 1520318172495977648L;

    public static String redirectToWebSite() {
        return "/index.xhtml?faces-redirect=true";
    }

    public static String redirectToProduct() {
        return "/product.xhtml?faces-redirect=true";
    }

    public static String redirectToProducts() {
        return "/products.xhtml?faces-redirect=true";
    }

    public static String redirectToLogin() {
        return "/manage/login.xhtml?faces-redirect=true";
    }

    public static String redirectToError() {
        return "/manage/error.xhtml?faces-redirect=true";
    }

    public static String redirectToWelcome() {
        return "/secured/mainpage.xhtml?faces-redirect=true";
    }

    public static String redirectToAddProduct() {
        return "/secured/addProduct.xhtml?faces-redirect=true";
    }

    public static String redirectToAddBrandAndType() {
        return "/secured/addBrandType.xhtml?faces-redirect=true";
    }

    public static String redirectToAddUser() {
        return "/secured/userOperations.xhtml?faces-redirect=true";
    }

    public static String redirectToMemberLogin() {
        return "/login.xhtml?faces-redirect=true";
    }

    public static String redirectToMembers() {
        return "/secured/members.xhtml?faces-redirect=true";
    }

    public static String redirectToOrders() {
        return "/secured/orders.xhtml?faces-redirect=true";
    }
    
    public static String redirectToCarts() {
        return "/secured/carts.xhtml?faces-redirect=true";
    }

    public static String redirectToMemberOrders() {
        return "/memberOrders.xhtml?faces-redirect=true";
    }

    public static String redirectToComments() {
        return "/secured/comments.xhtml?faces-redirect=true";
    }
}
