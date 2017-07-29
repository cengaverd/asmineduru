package com.asmineduru.model;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ERHAN
 */
@Entity
@Table(catalog = "asmine_main", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i")
    , @NamedQuery(name = "Image.findByImageId", query = "SELECT i FROM Image i WHERE i.imageId = :imageId")
    , @NamedQuery(name = "Image.findByProduct", query = "SELECT i FROM Image i WHERE i.product = :product")})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false)
    private byte[] image;

    @Basic(optional = false)
    @Column(nullable = false)
    private int usageStatus;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @Transient
    private String stringImage;
    
    @Transient
    private ImageLarge imageLarge;

    public Image() {
    }

    public Image(Integer imageId) {
        this.imageId = imageId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(int usageStatus) {
        this.usageStatus = usageStatus;
    }

    public String getStringImage() {
        StringBuilder sb = new StringBuilder();
        sb.append("data:image/png;base64,");
        sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(image, false)));
        stringImage = sb.toString();
        return stringImage;
    }

    public void setStringImage(String stringImage) {
        this.stringImage = stringImage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imageId != null ? imageId.hashCode() : 0);
        return hash;
    }

    public ImageLarge getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(ImageLarge imageLarge) {
        this.imageLarge = imageLarge;
    }
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.imageId == null && other.imageId != null) || (this.imageId != null && !this.imageId.equals(other.imageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asmineduru.model.Image[ imageId=" + imageId + " ]";
    }

}
