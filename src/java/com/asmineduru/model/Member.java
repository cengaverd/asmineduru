package com.asmineduru.model;

import com.asmineduru.util.MD5;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(catalog = "asmine_main", schema = "")
@XmlRootElement
public class Member implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "surname", nullable = false, length = 45)
    private String surname;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "token")
    private String token;
    @Column(name = "expiryDate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date expiryDate;

    @Column(name = "address", nullable = false, length = 500)
    private String address;
    @Column(name = "phone", nullable = false, length = 45)
    private String phone;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MD5.crypt(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
