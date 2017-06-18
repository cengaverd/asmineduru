package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Member;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    private final MainDao mainDao = new MainDao();
    private String subject;
    private String body;

    public void sendEmail(String mailAdresi, String konu) {
        final String username = "destek@asmineduru.com";
        final String password = "aS3421";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.asmineduru.com");
        props.put("mail.smtp.port", "2525");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            
            String token = UUID.randomUUID().toString();
            Member member=mainDao.findMemberByEmail(mailAdresi);
            member.setToken(token);
            mainDao.updateObject(member);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("destek@asmineduru.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("erhanyilmaz1983@hotmail.com"));
            message.setSubject("Şifremi Unuttum Uygulaması");
            message.setText("http://asmineduru.com/changePassword.xhtml?id="
                + member.getMemberId() + "&token=" + token);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    
}
