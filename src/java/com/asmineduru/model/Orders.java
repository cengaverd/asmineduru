package com.asmineduru.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author ERHAN
 */
@Entity
@Table(catalog = "asmine_main", schema = "")
@XmlRootElement
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    @Basic(optional = false)
    @Column(nullable = false)
    private String orderNumber;
    @ManyToOne
    @JoinColumn(name = "member")
    private Member member = new Member();
    @Basic(optional = false)
    @Column(nullable = false)
    private BigInteger totalPrice;
    @Basic(optional = false)
    @Column(nullable = false)
    private String cargoStatus;
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean payStatus;
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean usageStatus;

    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public Orders() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BigInteger getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigInteger totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public boolean isPayStatus() {
        return payStatus;
    }

    public void setPayStatus(boolean payStatus) {
        this.payStatus = payStatus;
    }

    public boolean isUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(boolean usageStatus) {
        this.usageStatus = usageStatus;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

}
