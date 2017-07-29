package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Member;
import com.asmineduru.util.MessagesController;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@SessionScoped
public class MemberSessionMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private Member member;
    private String email;
    private String password;
    private boolean loggedIn;

    public String login() {
        String result = null;
        try {

            FacesContext context = FacesContext.getCurrentInstance();
            ProductMB productMB = context.getApplication().evaluateExpressionGet(context, "#{productMB}", ProductMB.class);

            member = new Member();
            member.setEmail(email);
            member.setPassword(password);

            if (mainDao.loginControl(member)) {

                member = mainDao.findMemberByEmail(email);

                if (member.isActive()) {
                    loggedIn = true;
                    if (productMB!=null) {
                        productMB.findCartList();
                    }
                    result = NavigationBean.redirectToWebSite();
                } else {
                    MessagesController.uyariVer("E-posta adresinizdeki linke tıklayarak şifrenizi değiştirmelisiniz!");
                }

            } else {
                MessagesController.hataVer("E-posta adresi veya şifre hatalı!");
            }
        } catch (Exception e) {
            MessagesController.hataVer("Üye bilgileri kontrol edilirken hata oluştu");
        }
        return result;
    }

    public String quit() {
        try {
            member = new Member();
            email = null;
            password = null;
            loggedIn = false;
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            System.gc();

        } catch (Exception e) {
            MessagesController.hataVer("Çıkış işleminde hata oluştu");
        }

        return NavigationBean.redirectToWebSite();
    }

    public void forgetPassword() {
        try {

            if (email != null) {

                member = mainDao.findMemberByEmail(email);

                if (member != null) {
                    if (sendEmail(member.getEmail().trim(), "Asmine Duru Şifremi Unuttum Uygulaması")) {
                        MessagesController.bilgiVer("E-posta adresinize gönderilen maili onaylayınız!");
                    }
                } else {
                    MessagesController.bilgiVer("Kayıtlı üye bulunamadı!");

                }

            } else {
                MessagesController.uyariVer("E-posta adresinizi girmelisiniz!");
            }
        } catch (Exception e) {
            MessagesController.hataVer("Şifremi unuttum uygulamasında hata oluştu");
        }
    }

    public boolean sendEmail(String mailAdresi, String konu) {

        boolean sent;
        final String username = "destek@asmineduru.com";
        final String password = "aS3421";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.asmineduru.com");
        props.put("mail.smtp.port", "2525");
        System.setProperty("mail.mime.charset","UTF-8");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            String token = UUID.randomUUID().toString();
            Member member1 = mainDao.findMemberByEmail(mailAdresi);
            if (member1 != null) {
                member1.setToken(token);
                member1.setActive(false);
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.HOUR_OF_DAY, 9);
                member1.setExpiryDate(cal.getTime());
                mainDao.updateObject(member1);

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("destek@asmineduru.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(mailAdresi));
                message.setSubject(konu);
                message.setText("Şifrenizi değiştirmek için aşağıdaki linke tıklayınız. \r\n" + "http://www.asmineduru.com/changePassword.xhtml?id="
                        + member1.getMemberId() + "&token=" + token);

                Transport.send(message);
                sent = true;
            } else {
                MessagesController.uyariVer("Geçerli bir E-posta adresi girmelisiniz!");
                sent = false;
            }

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return sent;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
