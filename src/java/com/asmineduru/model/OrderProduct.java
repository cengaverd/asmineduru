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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ERHAN
 */
@Entity
@Table(catalog = "asmine_main", schema = "")
@XmlRootElement
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderProductId;
    @ManyToOne
    @JoinColumn(name = "orders")
    private Orders orders = new Orders();
    @ManyToOne
    @JoinColumn(name = "product")
    private Product product = new Product();    
    @Basic(optional = false)
    @Column(nullable = false)
    private BigInteger productPrice;
    @Basic(optional = false)
    @Column(nullable = false)
    private BigInteger quantity;

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigInteger getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigInteger productPrice) {
        this.productPrice = productPrice;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    
    
}
