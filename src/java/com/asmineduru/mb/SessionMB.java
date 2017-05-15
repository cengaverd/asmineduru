package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Users;
import com.asmineduru.util.MessagesController;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@SessionScoped
public class SessionMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private Users user = new Users();
    private boolean loggedIn;

    private boolean devemEtKontrol = false;

    public String login() {
        String result = null;
        try {
            if (mainDao.loginControl(user)) {

                loggedIn = true;
                user = mainDao.findObject(Users.class, user.getUsername());

                if (devemEtKontrol) {
                    devemEtKontrol = false;
                    return FacesContext.getCurrentInstance().getViewRoot().getViewId();
                } else {
                    result = NavigationBean.redirectToWelcome();
                }
            } else {
                MessagesController.hataVer("Kullanıcı adı veya şifre hatalı!");
            }
        } catch (Exception e) {
            MessagesController.hataVer("Kullanıcı kontrol edilirken hata oluştu");
        }
        return result;
    }

    public String quit() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            System.gc();
            return NavigationBean.redirectToLogin();
        } catch (Exception e) {
            MessagesController.hataVer("Çıkış işleminde hata oluştu");
            return null;
        }
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}
