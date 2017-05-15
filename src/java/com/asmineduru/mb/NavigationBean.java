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
}
