package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Member;
import com.asmineduru.util.MessagesController;
import com.asmineduru.util.Sabitler;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
public class RegistrationMB implements Serializable {

    private final MainDao mainDao = new MainDao();
    
    @ManagedProperty(value = "#{memberSessionMB}")
    private MemberSessionMB memberSessionMB;

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

    public String saveMember() {
        String result = null;
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
                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(new Date()); // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, 3); // adds one hour                
                member.setMemberDate(cal.getTime());
                member.setActive(true);
                mainDao.saveObject(member);  
                mainDao.sendEmail(Sabitler.MAIL_ADRESS, "Asmine Duru Yeni Üye", member.getUsername()+" kullanıcı adlı yeni bir üyeniz olmuştur.");
                memberSessionMB.setEmail(member.getEmail());
                memberSessionMB.setPassword(password);
                member = new Member();
                memberSessionMB.login();
                result = NavigationBean.redirectToWebSite();
            }

        } catch (Exception e) {

            MessagesController.hataVer("Hesap oluşturma işleminde hata oluştu.");
        }
        return result;
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
