package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Member;
import com.asmineduru.util.MessagesController;
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
public class RegistrationMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private Member member;
    private String password;
    private String password2;

    @PostConstruct
    public void init() {
        try {
            member = new Member();

        } catch (Exception e) {
        }
    }

    public void saveMember() {
        try {

            List<Member> memberList = mainDao.findAllMembers();
            boolean continueProcess = true;

            if (memberList != null && !memberList.isEmpty()) {
                for (Member databaseMember : memberList) {
                    if (member.getEmail().equals(databaseMember.getEmail())) {
                        MessagesController.uyariVer("İlgili E-posta adresi ile daha önceden hesap oluşturulmuş.");
                        continueProcess = false;
                        break;
                    }

                    if (member.getUsername().equals(databaseMember.getUsername())) {
                        MessagesController.uyariVer("Kullanıcı adı ile daha önceden hesap oluşturulmuş.");
                        continueProcess = false;
                        break;
                    }
                }
            }
            if (continueProcess) {
                member.setPassword(password);
                member.setActive(true);
                mainDao.saveObject(member);
                member = new Member();
                MessagesController.bilgiVer("Hesabınız oluşturulmuştur. Giriş yapabilirsiniz.");
            }

        } catch (Exception e) {

            MessagesController.hataVer("Hesap oluşturma işleminde hata oluştu.");
        }
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

}
