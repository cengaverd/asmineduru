package com.asmineduru.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(catalog = "asmine_main", schema = "")
@XmlRootElement
public class Cart implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;
    @OneToOne
    @JoinColumn(name="product")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;    
    @Basic(optional = false)
    @Column(nullable = false)
    private BigInteger quantity;    
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean usageStatus;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }



    public boolean isUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(boolean usageStatus) {
        this.usageStatus = usageStatus;
    }

    
}
