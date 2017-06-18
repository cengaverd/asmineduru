package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Member;
import com.asmineduru.model.Product;
import com.asmineduru.util.MessagesController;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@ViewScoped
public class ChangePasswordMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private String token;
    private Integer memberId;
    private String password;
    private String password2;
    private boolean viewChangePassword;
    private Member member;
    private boolean viewLoginButon=false;

    public void loadData() {
        try {

            member = mainDao.findMemberByMemberIdAndToken(memberId, token);

            if (member != null) {

                Calendar cal = Calendar.getInstance();
                if ((member.getExpiryDate()
                        .getTime() - cal.getTime()
                                .getTime()) <= 0) {
                    viewChangePassword = false;
                    MessagesController.uyariVer("E-posta adresinizden şifreniz için doğrulama süresi geçmiş. Tekrar şifremi unuttum demelisiniz.");
                } else {
                    viewChangePassword = true;
                }
            } else {
                MessagesController.uyariVer("Üye mevcut değildir.");
                viewChangePassword = false;
            }

        } catch (Exception e) {
        }
    }

    public void saveMemberPassword() {
        try {

            member.setPassword(password);
            member.setToken(null);
            member.setExpiryDate(null);
            member.setActive(true);
            mainDao.updateObject(member);
            MessagesController.uyariVer("Şifreniz değiştirilmiştir.Giriş yapabilirsiniz.");
            viewLoginButon=true;

        } catch (Exception e) {

        }
    }

///////////////////// Getter ve Setter ////////////////////////////////////////
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public boolean isViewChangePassword() {
        return viewChangePassword;
    }

    public void setViewChangePassword(boolean viewChangePassword) {
        this.viewChangePassword = viewChangePassword;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public boolean isViewLoginButon() {
        return viewLoginButon;
    }

    public void setViewLoginButon(boolean viewLoginButon) {
        this.viewLoginButon = viewLoginButon;
    }

}
