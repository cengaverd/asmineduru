/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asmineduru.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ERHAN
 */
@Entity
@Table(catalog = "asmine_main", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Type.findAll", query = "SELECT t FROM Type t")
    , @NamedQuery(name = "Type.findByTypeId", query = "SELECT t FROM Type t WHERE t.typeId = :typeId")
    , @NamedQuery(name = "Type.findByBrand", query = "SELECT t FROM Type t WHERE t.brand = :brand")
    , @NamedQuery(name = "Type.findByTypeName", query = "SELECT t FROM Type t WHERE t.typeName = :typeName")
    , @NamedQuery(name = "Type.findByUsageStatus", query = "SELECT t FROM Type t WHERE t.usageStatus = :usageStatus")})
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer typeId;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String typeName;
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean usageStatus;
    @ManyToOne
    @JoinColumn(name = "brand")
    private Brand brand;

    public Type() {
    }

    public Type(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(boolean usageStatus) {
        this.usageStatus = usageStatus;
    }
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeId != null ? typeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Type)) {
            return false;
        }
        Type other = (Type) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asmineduru.model.Type[ typeId=" + typeId + " ]";
    }
    
}
