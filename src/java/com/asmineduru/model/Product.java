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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

/**
 *
 * @author ERHAN
 */
@Entity
@Table(catalog = "asmine_main", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByProductId", query = "SELECT p FROM Product p WHERE p.productId = :productId")
    , @NamedQuery(name = "Product.findByType", query = "SELECT p FROM Product p WHERE p.type = :type")
    , @NamedQuery(name = "Product.findByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName")
    , @NamedQuery(name = "Product.findByProductPrice", query = "SELECT p FROM Product p WHERE p.productPrice = :productPrice")
    , @NamedQuery(name = "Product.findByProductFirstPrice", query = "SELECT p FROM Product p WHERE p.productFirstPrice = :productFirstPrice")
    , @NamedQuery(name = "Product.findByUsageStatus", query = "SELECT p FROM Product p WHERE p.usageStatus = :usageStatus")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;    
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String productName;
    @Basic(optional = false)
    @Column(nullable = false)
    private BigInteger productPrice;
    @Basic(optional = false)
    @Column(nullable = false)
    private BigInteger productFirstPrice;
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean usageStatus;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String productCode;
    @Basic(optional = false)
    @Column
    private boolean showLeftSide;
    @Basic(optional = false)
    @Column
    private boolean showMainPage;
    @Basic(optional = false)
    @Column(length = 500)
    private String description;
    
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @Fetch (FetchMode.SELECT)
    private List<Image> imageList=new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "type")
    private Type type=new Type();
    
    @Formula("((productFirstPrice-productPrice)*100)/productFirstPrice")
    private Integer discount;
    
    @Formula("(select count(*) from Likes l where l.productId=productId)")
    private Integer likeCount;
    
    @Formula("(select count(*) from Comment c where c.productId=productId and c.usageStatus=1)")
    private Integer commentCount;
    
    @Transient
    private boolean memberFavorite;

    public Product() {
    }

    public Product(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigInteger getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigInteger productPrice) {
        this.productPrice = productPrice;
    }

    public BigInteger getProductFirstPrice() {
        return productFirstPrice;
    }

    public void setProductFirstPrice(BigInteger productFirstPrice) {
        this.productFirstPrice = productFirstPrice;
    }   

    public boolean isUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(boolean usageStatus) {
        this.usageStatus = usageStatus;
    }   

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }  

    public boolean isShowLeftSide() {
        return showLeftSide;
    }

    public void setShowLeftSide(boolean showLeftSide) {
        this.showLeftSide = showLeftSide;
    }

    public boolean isShowMainPage() {
        return showMainPage;
    }

    public void setShowMainPage(boolean showMainPage) {
        this.showMainPage = showMainPage;
    }    

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isMemberFavorite() {
        return memberFavorite;
    }

    public void setMemberFavorite(boolean memberFavorite) {
        this.memberFavorite = memberFavorite;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asmineduru.model.Product[ productId=" + productId + " ]";
    }
    
}
