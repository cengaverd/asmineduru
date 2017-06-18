package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Brand;
import com.asmineduru.model.Image;
import com.asmineduru.model.Product;
import com.asmineduru.model.Type;
import com.asmineduru.util.MessagesController;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@ViewScoped
public class AddProductMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private Product product = new Product();
    private Image image = new Image();
    private UploadedFile uploadedFile;
    private StreamedContent dosya = null;

    private Type type;
    private List<Type> typeList;
    private Brand brand;
    private List<Brand> brandList;

    private List<Product> productList;
    private Product selectedProduct = new Product();
    private boolean showUpdate;

    private List<Image> imageList;
    private Image selectedImage;

    @PostConstruct
    public void init() {
        try {

            brandList = mainDao.findAllBrandInUsage();
            productList = mainDao.findAllProduct();
            imageList = new ArrayList<>();

        } catch (Exception e) {
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        uploadedFile = event.getFile();
        image = new Image();
        image.setImageShow(new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents())));
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(uploadedFile.getContents()));
        int width = bi.getWidth();
        int height = bi.getHeight();
        byte[] original = scale(uploadedFile.getContents(), width, height);
        image.setOriginImage(original);
        image.setImage(scale(uploadedFile.getContents(), 250, 320));
        image.setProduct(selectedProduct);
        image.setUsageStatus(1);
        imageList.add(image);
    }

    public void saveProduct() {
        try {

            selectedProduct.setType(type);
            String brandFirstChar = type.getBrand().getBrandName().substring(0, 1);
            Integer maxId = mainDao.findProductMaxId();
            String code;

            if (maxId != null) {
                code = brandFirstChar+"-" + String.format("%04d", maxId + 1);
            } else {
                code = brandFirstChar+"-" + String.format("%04d", 1);
            }

            selectedProduct.setProductCode(code);
            selectedProduct.setUsageStatus(true);
            selectedProduct.setImageList(imageList);

            mainDao.saveProductAndImageList(selectedProduct);
            productList = mainDao.findAllProduct();
            clearAddProduct();
            MessagesController.bilgiVer("Kaydetme işlemi yapıldı.");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').hide();");

        } catch (Exception e) {

            MessagesController.hataVer("Kaydetme işleminde hata oluştu.");
        }
    }

    public void findProductForUpdate() {
        try {

            imageList = new ArrayList<>();
            showUpdate = true;
            brand = selectedProduct.getType().getBrand();
            typeList = mainDao.findAllTypeByBrand(brand);
            type = selectedProduct.getType();
            brand = type.getBrand();
            image = selectedProduct.getImageList().get(0);
            imageList.addAll(selectedProduct.getImageList());
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').show();");

        } catch (Exception e) {

            MessagesController.hataVer("Güncelleme işleminde hata oluştu.");
        }
    }

    public void updateProduct() {
        try {

            selectedProduct.setType(type);
            selectedProduct.getImageList().addAll(imageList);
            mainDao.saveOrUpdateProductAndImageList(selectedProduct);
            productList = mainDao.findAllProduct();
            clearAddProduct();
            MessagesController.bilgiVer("Ürün bilgileri güncellenmiştir.");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').hide();");

        } catch (Exception e) {

            MessagesController.hataVer("Güncelleme işleminde hata oluştu.");
        }
    }

    public void findTypeList() {
        try {

            typeList = mainDao.findAllTypeByBrand(brand);

        } catch (Exception e) {
        }
    }

    public byte[] scale(byte[] fileData, int width, int height) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            if (height == 0) {
                height = (width * img.getHeight()) / img.getWidth();
            }
            if (width == 0) {
                width = (height * img.getWidth()) / img.getHeight();
            }
            java.awt.Image scaledImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = imageBuff.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, width, height, null);
            Color color = new Color(0.9f, 1, 0.8f, 0.3f);
            g2d.setPaint(color);
            g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
            String s = "Asmine Duru";
            int x = (width / 2) - 55;
            int y = height / 2;
            g2d.drawString(s, x, y);
            g2d.dispose();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            return buffer.toByteArray();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void clearAddProduct() {
        try {
            showUpdate = false;
            product = new Product();
            image = new Image();
            type = new Type();
            brand = new Brand();
            if (typeList != null && !typeList.isEmpty()) {
                typeList.clear();
            }
            imageList.clear();
            selectedProduct = new Product();

        } catch (Exception e) {
            MessagesController.hataVer("Temizleme işleminde hata oluştu");
        }
    }

    public void deleteProduct() {
        try {
            mainDao.deleteProductAndImageList(selectedProduct);
            productList = mainDao.findAllProduct();
            MessagesController.bilgiVer("Ürün silinmiştir.");
        } catch (Exception e) {
            MessagesController.hataVer("Ürün silme işleminde hata oluştu");
        }
    }

    public void deleteImage() {
        try {
            imageList.remove(selectedImage);
            mainDao.deleteObject(selectedImage);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('resimSil').hide();");
        } catch (Exception e) {
            MessagesController.hataVer("Resim silme işleminde hata oluştu");
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public StreamedContent getDosya() {
        return dosya;
    }

    public void setDosya(StreamedContent dosya) {
        this.dosya = dosya;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public boolean isShowUpdate() {
        return showUpdate;
    }

    public void setShowUpdate(boolean showUpdate) {
        this.showUpdate = showUpdate;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Image getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Image selectedImage) {
        this.selectedImage = selectedImage;
    }

}
