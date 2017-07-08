package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Member;
import com.asmineduru.util.MessagesController;
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
public class SettingsMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    @ManagedProperty(value = "#{memberSessionMB}")
    private MemberSessionMB memberSessionMB;

    private Member member;
    private String password;
    private String password2;

    @PostConstruct
    public void init() {
        try {
            if (memberSessionMB.getMember() != null) {
                member = memberSessionMB.getMember();
            }
        } catch (Exception e) {
        }
    }

    public void saveMember() {
        try {

            if (!password.equals("") && !password2.equals("")) {

                member.setPassword(password);
                mainDao.updateObject(member);
                memberSessionMB.setMember(member);
                MessagesController.bilgiVer("Bilgileriniz güncellenmiştir.");

            } else {

                if (password.equals("")) {
                    MessagesController.uyariVer("Lütfen şifrenizi giriniz.");
                } else if (password2.equals("")) {
                    MessagesController.uyariVer("Şifre ve şifre tekrarı aynı olmalıdır.");
                }
            }

        } catch (Exception e) {

            MessagesController.hataVer("Kaydetme işleminde hata oluştu.");
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

    public MemberSessionMB getMemberSessionMB() {
        return memberSessionMB;
    }

    public void setMemberSessionMB(MemberSessionMB memberSessionMB) {
        this.memberSessionMB = memberSessionMB;
    }

}
