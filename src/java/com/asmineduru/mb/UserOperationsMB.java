package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Brand;
import com.asmineduru.model.Type;
import com.asmineduru.model.Users;
import com.asmineduru.util.MessagesController;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@ViewScoped
public class UserOperationsMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private List<Users> userList;
    private Users user;
    private Users selectedUser;
    private String password;
    private String password2;

    private boolean showUpdate;

    @PostConstruct
    public void init() {
        try {

            userList = mainDao.findAllUsers();

        } catch (Exception e) {
        }
    }

    public void saveUser() {
        try {
            
            selectedUser.setPassword(password);
            mainDao.saveObject(selectedUser);
            userList = mainDao.findAllUsers();
            clearAddUser();
            MessagesController.bilgiVer("Kaydetme işlemi yapıldı.");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').hide();");

        } catch (Exception e) {

            MessagesController.hataVer("Kaydetme işleminde hata oluştu.");
        }
    }

    public void findUserForUpdate() {
        try {

            showUpdate = true;
            password=null;
            password2=null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').show();");

        } catch (Exception e) {

            MessagesController.hataVer("Güncelleme işleminde hata oluştu.");
        }
    }

    public void updateUser() {
        try {

            mainDao.saveOrUpdateObject(selectedUser);
            userList = mainDao.findAllUsers();
            clearAddUser();
            MessagesController.bilgiVer("Kullanıcı bilgileri güncellenmiştir.");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').hide();");

        } catch (Exception e) {

            MessagesController.hataVer("Güncelleme işleminde hata oluştu.");
        }
    }

    public void deleteUser() {
        try {
            mainDao.deleteObject(selectedUser);
            userList = mainDao.findAllUsers();
            MessagesController.bilgiVer("Kullanıcı silinmiştir.");
        } catch (Exception e) {
            MessagesController.hataVer("Kullanıcı silme işleminde hata oluştu");
        }
    }

    public void clearAddUser() {
        try {
            showUpdate = false;
            user = new Users();
            selectedUser = new Users();
            password=null;
            password2=null;

        } catch (Exception e) {
            MessagesController.hataVer("Temizleme işleminde hata oluştu");
        }
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public boolean isShowUpdate() {
        return showUpdate;
    }

    public void setShowUpdate(boolean showUpdate) {
        this.showUpdate = showUpdate;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
