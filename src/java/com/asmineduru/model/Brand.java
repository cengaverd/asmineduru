/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asmineduru.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

/**
 *
 * @author ERHAN
 */
@Entity
@Table(catalog = "asmine_main", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Brand.findAll", query = "SELECT b FROM Brand b")
    , @NamedQuery(name = "Brand.findByBrandId", query = "SELECT b FROM Brand b WHERE b.brandId = :brandId")
    , @NamedQuery(name = "Brand.findByBrandName", query = "SELECT b FROM Brand b WHERE b.brandName = :brandName")
    , @NamedQuery(name = "Brand.findByUsageStatus", query = "SELECT b FROM Brand b WHERE b.usageStatus = :usageStatus")})
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brandId;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String brandName;
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean usageStatus;

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Type> typeList = new ArrayList<>();

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Where(clause = "usageStatus=1")
    private List<Type> typeListForMenu = new ArrayList<>();

    public Brand() {
    }

    public Brand(Integer brandId) {
        this.brandId = brandId;
    }

    public Brand(Integer brandId, String brandName, boolean usageStatus) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.usageStatus = usageStatus;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public boolean isUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(boolean usageStatus) {
        this.usageStatus = usageStatus;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public List<Type> getTypeListForMenu() {
        return typeListForMenu;
    }

    public void setTypeListForMenu(List<Type> typeListForMenu) {
        this.typeListForMenu = typeListForMenu;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (brandId != null ? brandId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Brand)) {
            return false;
        }
        Brand other = (Brand) object;
        if ((this.brandId == null && other.brandId != null) || (this.brandId != null && !this.brandId.equals(other.brandId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asmineduru.model.Brand[ brandId=" + brandId + " ]";
    }

}
