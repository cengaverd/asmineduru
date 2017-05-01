package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Product;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@ViewScoped
public class ProductMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private Product selectedProduct;
    private Integer productId;
    private String contourChart;

    public void loadData() {
        try {

            selectedProduct = mainDao.findProductByProductId(productId);

            StringBuilder sb = new StringBuilder();
            sb.append("data:image/png;base64,");
            sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(selectedProduct.getImageList().get(0).getImage(), false)));
            contourChart = sb.toString();

        } catch (Exception e) {

        }
    }

///////////////////// Getter ve Setter ////////////////////////////////////////
    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getContourChart() {
        return contourChart;
    }

    public void setContourChart(String contourChart) {
        this.contourChart = contourChart;
    }

}
