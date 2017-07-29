package com.asmineduru.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

/**
 *
 * @author ERHAN
 */
@Entity
@Table(catalog = "asmine_main", schema = "")
public class ImageLarge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageLargeId;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false)
    private byte[] imageLarge;   

    @OneToOne
    @JoinColumn(name = "image")
    private Image image;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private int usageStatus;

    @Transient
    private String stringImageLarge;


    public ImageLarge() {
    }

    public Integer getImageLargeId() {
        return imageLargeId;
    }

    public void setImageLargeId(Integer imageLargeId) {
        this.imageLargeId = imageLargeId;
    }

    public byte[] getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(byte[] imageLarge) {
        this.imageLarge = imageLarge;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(int usageStatus) {
        this.usageStatus = usageStatus;
    }

    public String getStringImageLarge() {
        StringBuilder sb = new StringBuilder();
        sb.append("data:image/png;base64,");
        sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(imageLarge, false)));
        stringImageLarge = sb.toString();
        return stringImageLarge;
    }

    public void setStringImageLarge(String stringImageLarge) {
        this.stringImageLarge = stringImageLarge;
    }    
}
